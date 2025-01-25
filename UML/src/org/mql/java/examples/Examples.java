package org.mql.java.examples;

import org.mql.java.models.ClassEntity;
import org.mql.java.models.Project;
import org.mql.java.models.UMLRelation;
import org.mql.java.service.PackageExplorer;
import org.mql.java.xml.XMLExporter;
import org.mql.java.xml.XMLParser;
import org.mql.java.xml.XMLViewer;

public class Examples {

	private Project project;

	public Examples() {
		ex01();
		// ex02();
//         ex03();
	}

	public void ex01() {
		project = new Project();
		new PackageExplorer("org.mql.java.models", project);
	}

	public void ex02() {
		project = new Project();
		new PackageExplorer("org.mql.java", project);

		// Export to XML
		String outputPath = "../UML/resources/xmlFiles/project.xml";
		XMLExporter.exportToXML(project, outputPath);

//        XMLViewer.displayXML(outputPath);
	}

	public void ex03() {

//        String xmlPath = "../UML/resources/xmlFiles/project.xml";
//        XMLParser.parse(xmlPath);

//        xmi        
		String xmiPath = "../UML/resources/xmlFiles/xmiFile.xmi";
		XMLParser.parse(xmiPath);
	}

	
	public static void main(String[] args) {
		new Examples();
	}
}
