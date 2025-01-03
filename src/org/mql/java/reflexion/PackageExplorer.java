package org.mql.java.reflexion;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mql.java.models.ClassEntity;
import org.mql.java.models.PackageEntity;
import org.mql.java.models.Project;
import org.mql.java.models.UMLRelation;

public class PackageExplorer {

	private Project project;

	public PackageExplorer() {
		project = new Project();
		scan("org.mql.java", project);
		displayResults();
	}

	// le paramètre "project" agit comme un conteneur qui rassemble tous packages et
	// leurs classes associées.
	public void scan(String packageName, Project project) {
		String classPath = System.getProperty("java.class.path");
		String packagePath = packageName.replace(".", "\\");
		String path = classPath + "\\" + packagePath;
		File dir = new File(path);
		File[] content = dir.listFiles();
//		System.out.println(path);
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

	private void analyzeClass(String className, PackageEntity packageEntity) {
		Class<?> cls;
		try {
			cls = Class.forName(className);
			ClassEntity classEntity = new ClassEntity(cls.getSimpleName());
			classEntity.setInterface(cls.isInterface());
			classEntity.setEnum(cls.isEnum());

			for (var annotation : cls.getAnnotations()) {
				classEntity.addAnnotation(annotation.annotationType().getSimpleName());
			}

			// Traitement spécifique pour les énumérations
			if (cls.isEnum()) {
				Object[] enumConstants = cls.getEnumConstants();
				List<String> constants = new ArrayList<>();
				for (Object constant : enumConstants) {
					constants.add(constant.toString());
				}
				// Ajout des constantes de l'énumération
				classEntity.setEnumConstants(constants);
			}

			if (cls.getSuperclass() != null && !cls.getSuperclass().equals(Object.class)) {
				UMLRelation inheritanceRelation = new UMLRelation("InheritsFrom", classEntity,
						new ClassEntity(cls.getSuperclass().getSimpleName()));
				packageEntity.addRelation(inheritanceRelation);
			}

			// Collect Implementations (Interfaces)
			for (Class<?> iface : cls.getInterfaces()) {
				UMLRelation implementsRelation = new UMLRelation("Implements", classEntity,
						new ClassEntity(iface.getSimpleName()));
				packageEntity.addRelation(implementsRelation);
			}

			// Traitement spécifique pour les énumérations
			if (cls.isEnum()) {
				Object[] enumConstants = cls.getEnumConstants();
				List<String> constants = new ArrayList<>();
				for (Object constant : enumConstants) {
					constants.add(constant.toString());
				}
				// Ajout des constantes de l'énumération
				classEntity.setEnumConstants(constants);
			}

			// Extraction des champs
			for (var field : cls.getDeclaredFields()) {
				String fieldDetails = field.getName() + " : " + field.getType().getSimpleName();
				classEntity.addField(fieldDetails);
			}

			// Extraction des méthodes
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

			// Relations basées sur les champs
			for (var field : cls.getDeclaredFields()) {
				String fieldType = field.getType().getSimpleName();

				// Relation de composition : si le champ est une classe
				if (!field.getType().isPrimitive() && !fieldType.startsWith("java.")) {
					UMLRelation compositionRelation = new UMLRelation("Composes", classEntity,
							new ClassEntity(fieldType));
					packageEntity.addRelation(compositionRelation);
				}

				// Ajout du champ dans les métadonnées de la classe
				String fieldDetails = field.getName() + " : " + fieldType;
				classEntity.addField(fieldDetails);
			}

			// Analyse des annotations personnalisées sur la classe
			for (var annotation : cls.getAnnotations()) {
				String annotationName = annotation.annotationType().getSimpleName();

				// Déterminer le type de relation en fonction de l'annotation
				String relationType = switch (annotationName) {
				case "Aggregates" -> "Aggregates";
				case "Uses" -> "Uses";
				case "DependsOn" -> "DependsOn";
				case "Composes" -> "Composes";
				case "Associates" -> "Associates";
				default -> null;
				};

				if (relationType != null) {
					// Créer une relation en utilisant les annotations
					UMLRelation annotationRelation = new UMLRelation(relationType, classEntity,
							new ClassEntity(annotation.annotationType().getSimpleName()));
					packageEntity.addRelation(annotationRelation);
				}

				// Ajout de l'annotation dans les métadonnées de la classe
				classEntity.addAnnotation(annotationName);
			}

			// Add ClassEntity to the package
			packageEntity.addClass(classEntity);
		} catch (ClassNotFoundException e) {
			System.err.println("Class not found: " + className);
		}

	}

	private void displayResults() {
		System.out.println("Structure du projet :");
		for (PackageEntity pkg : project.getPackages()) {
			System.out.println("Package : " + pkg.getName());
			for (ClassEntity cls : pkg.getClasses()) {
				System.out.print(" - " + cls.getName());
				if (cls.isInterface())
					System.out.print(" [Interface]");
				if (cls.isEnum())
					System.out.print(" [Enum]");
				if (!cls.getAnnotations().isEmpty()) {
					System.out.print(" Annotations: " + cls.getAnnotations());
				}
				System.out.println();

				// Afficher les constantes des enums
				if (cls.isEnum() && cls.getEnumConstants() != null) {
					System.out.println("   Constantes: " + cls.getEnumConstants());
				}

				// Afficher les champs
				if (!cls.getFields().isEmpty()) {
					System.out.println("   Champs: ");
					for (String field : cls.getFields()) {
						System.out.println("     - " + field);
					}
				}

				// Afficher les méthodes
				if (!cls.getMethods().isEmpty()) {
					System.out.println("   Méthodes: ");
					for (String method : cls.getMethods()) {
						System.out.println("     - " + method);
					}
				}
				
				 // Afficher les relations
			    if (!pkg.getRelations().isEmpty()) {
			        System.out.println("   Relations: ");
			        for (UMLRelation relation : pkg.getRelations()) {
			            System.out.println("     - " + relation.getType() +
			                " entre " + relation.getSource().getName() +
			                " et " + relation.getTarget().getName());
			        }
			    }
			}

		}
	}

	public static void main(String[] args) {
		new PackageExplorer();
	}
}
