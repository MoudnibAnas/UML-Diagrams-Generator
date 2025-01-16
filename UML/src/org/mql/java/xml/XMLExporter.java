package org.mql.java.xml;
import org.mql.java.models.ClassEntity;
import org.mql.java.models.PackageEntity;
import org.mql.java.models.Project;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XMLExporter {

    public static void exportToXML(Project project, String fileName) {
        try {
            // Initialize XML document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            // Create root element
            Element root = document.createElement("Project");
            document.appendChild(root);

            // Add packages and classes dynamically
            for (PackageEntity pkg : project.getPackages()) {
                Element packageElement = document.createElement("Package");
                packageElement.setAttribute("name", pkg.getName());
                root.appendChild(packageElement);

                for (ClassEntity cls : pkg.getClasses()) {
                    Element classElement = document.createElement("Class");
                    classElement.setAttribute("name", cls.getName());
                    classElement.setAttribute("type", cls.isInterface() ? "interface" : cls.isEnum() ? "enum" : "class");

                    // Add fields
                    for (String field : cls.getFields()) {
                        Element fieldElement = document.createElement("Field");
                        fieldElement.setTextContent(field);
                        classElement.appendChild(fieldElement);
                    }

                    // Add methods
                    for (String method : cls.getMethods()) {
                        Element methodElement = document.createElement("Method");
                        methodElement.setTextContent(method);
                        classElement.appendChild(methodElement);
                    }

                    // Add annotations
                    for (String annotation : cls.getAnnotations()) {
                        Element annotationElement = document.createElement("Annotation");
                        annotationElement.setTextContent(annotation);
                        classElement.appendChild(annotationElement);
                    }

                    packageElement.appendChild(classElement);
                }
            }

            // Transform document to XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(fileName));

            transformer.transform(source, result);
            System.out.println("XML Exported Successfully: " + fileName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}