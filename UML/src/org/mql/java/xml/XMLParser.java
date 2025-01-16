package org.mql.java.xml;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParser {

    public static void parse(String filePath) {
        try {
            File xmlFile = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true); // For handling namespaces
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            // Normalize the document
            document.getDocumentElement().normalize();

            // Start parsing from the root element
            Element root = document.getDocumentElement();
            System.out.println("Root Element: " + root.getNodeName());

            parseNode(root, 0);

            System.out.println("XML Parsed Successfully: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to parse XML file: " + filePath);
        }
    }

    private static void parseNode(Node node, int depth) {
        // Print node details
        printIndent(depth);
        System.out.println("Node: " + node.getNodeName());

        // Print attributes if available
        if (node.hasAttributes()) {
            var attributes = node.getAttributes();
            for (int i = 0; i < attributes.getLength(); i++) {
                var attribute = attributes.item(i);
                printIndent(depth + 1);
                System.out.println("Attribute: " + attribute.getNodeName() + " = " + attribute.getNodeValue());
            }
        }

        // Print text content if present and not empty
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            String textContent = node.getTextContent().trim();
            if (!textContent.isEmpty() && node.getChildNodes().getLength() == 1) {
                printIndent(depth + 1);
                System.out.println("Value: " + textContent);
            }
        }

        // Recursively parse child nodes
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node child = childNodes.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                parseNode(child, depth + 1);
            }
        }
    }

    private static void printIndent(int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print("    ");
        }
    }
}
