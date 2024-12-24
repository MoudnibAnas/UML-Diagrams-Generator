package org.mql.java.reflexion;

import java.io.File;

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
			}
		}
	}

	public static void main(String[] args) {
		new PackageExplorer();
	}
}
