package org.mql.java.reflexion;

import java.io.File;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.mql.java.models.ClassEntity;
import org.mql.java.models.PackageEntity;
import org.mql.java.models.Project;
import org.mql.java.models.UMLRelation;
import org.mql.java.xml.XMLExporter;

public class PackageExplorer {

	private static  Project project;

	public PackageExplorer() {
	}

	public PackageExplorer(String packageName, Project project) {
		this.project = project;
		scan(packageName, project);
		displayResults();
		XMLExporter.exportToXML(project, "../UML/resources/xmlFiles/project.xml");
	}

	public static void scan(String packageName, Project project) {
		String classPath = System.getProperty("java.class.path");
		String packagePath = packageName.replace(".", File.separator);
		String path = classPath + File.separator + packagePath;
		File dir = new File(path);
		File[] content = dir.listFiles();
		if (content == null)
			return;

		PackageEntity packageEntity = new PackageEntity(packageName);
		project.addPackage(packageEntity);

		for (File file : content) {
			if (file.isDirectory()) {
				scan(packageName + "." + file.getName(), project);
			} else if (file.getName().endsWith(".class")) {
				String className = packageName + "." + file.getName().replace(".class", "");
				analyzeClass(className, packageEntity);
			}
		}
	}

	private static void analyzeClass(String className, PackageEntity packageEntity) {
		try {
			Class<?> cls = Class.forName(className);
			ClassEntity classEntity = new ClassEntity(cls.getSimpleName());
			classEntity.setInterface(cls.isInterface());
			classEntity.setEnum(cls.isEnum());

			for (var annotation : cls.getAnnotations()) {
				classEntity.addAnnotation(annotation.annotationType().getSimpleName());
			}

			if (cls.isEnum()) {
				Object[] enumConstants = cls.getEnumConstants();
				List<String> constants = new ArrayList<>();
				for (Object constant : enumConstants) {
					constants.add(constant.toString());
				}
				classEntity.setEnumConstants(constants);
			}

			if (cls.getSuperclass() != null && !cls.getSuperclass().equals(Object.class)) {
				UMLRelation inheritanceRelation = new UMLRelation("InheritsFrom", classEntity,
						new ClassEntity(cls.getSuperclass().getSimpleName()));
				packageEntity.addRelation(inheritanceRelation);
			}

			for (Class<?> iface : cls.getInterfaces()) {
				UMLRelation implementsRelation = new UMLRelation("Implements", classEntity,
						new ClassEntity(iface.getSimpleName()));
				packageEntity.addRelation(implementsRelation);
			}

			for (var field : cls.getDeclaredFields()) {
				String fieldType = field.getType().getSimpleName();

				if (!field.getType().isPrimitive() && !fieldType.startsWith("java.")) {
					UMLRelation compositionRelation = new UMLRelation("Composes", classEntity,
							new ClassEntity(fieldType));
					packageEntity.addRelation(compositionRelation);
				}

				String fieldDetails = field.getName() + " : " + fieldType;
				classEntity.addField(fieldDetails);
			}

			for (var method : cls.getDeclaredMethods()) {
				StringBuilder methodDetails = new StringBuilder();
				methodDetails.append(method.getName()).append("(");
				var params = method.getParameterTypes();
				for (int i = 0; i < params.length; i++) {
					methodDetails.append(params[i].getSimpleName());
					if (i < params.length - 1) {
						methodDetails.append(", ");
					}
				}
				methodDetails.append(") : ").append(method.getReturnType().getSimpleName());
				classEntity.addMethod(methodDetails.toString());
			}

			packageEntity.addClass(classEntity);
		} catch (ClassNotFoundException e) {
			System.err.println("Class not found: " + className);
		}
	}

	public static void displayResults() {
		System.out.println("Structure du projet :");
		for (PackageEntity pkg : project.getPackages()) {
			System.out.println("Package : " + pkg.getName());
			for (ClassEntity cls : pkg.getClasses()) {
				System.out.print(" - Classe : " + cls.getName());
				if (cls.isInterface())
					System.out.print(" [Interface]");
				if (cls.isEnum())
					System.out.print(" [Enum]");
				if (!cls.getAnnotations().isEmpty()) {
					System.out.print(" Annotations: " + cls.getAnnotations());
				}
				System.out.println();

				if (cls.isEnum() && cls.getEnumConstants() != null) {
					System.out.println("   Constantes: " + cls.getEnumConstants());
				}

				if (!cls.getFields().isEmpty()) {
					System.out.println("   Champs: ");
					for (String field : cls.getFields()) {

						String visibility = getVisibilityPrefix(field);
						System.out.println("     " + visibility + " " + field);

					}
				}

				if (!cls.getMethods().isEmpty()) {
					System.out.println("   Méthodes: ");
					for (String method : cls.getMethods()) {
						System.out.println("     - " + method);
					}
				}
			}

			if (!pkg.getRelations().isEmpty()) {
				System.out.println("   Relations: ");
				for (UMLRelation relation : pkg.getRelations()) {
					System.out.println("     - " + relation.getType() + " entre " + relation.getSource().getName()
							+ " et " + relation.getTarget().getName());
				}
			}
		}
	}

	private static String getVisibilityPrefix(String member) {
		// Example: Adjust to parse visibility modifiers
		if (member.startsWith("public")) {
			return "+";
		} else if (member.startsWith("private")) {
			return "-";
		} else if (member.startsWith("protected")) {
			return "#";
		} else if (member.contains("static")) {
			return "{static}";
		} else {
			return "~"; // Default package-private visibility
		}
	}
}
