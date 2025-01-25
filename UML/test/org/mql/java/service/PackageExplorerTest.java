package org.mql.java.service;

import org.junit.jupiter.api.Test;
import org.mql.java.models.Project;
import org.mql.java.service.PackageExplorer;
import org.mql.java.xml.XMLExporter;
import org.mql.java.xml.XMLParser;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class PackageExplorerTest {


    @Test
    void testXMLExporter() {
        Project project = new Project();
        new PackageExplorer("org.mql.java", project);

        String outputPath = "../UML/resources/xmlFiles/project.xml";
        XMLExporter.exportToXML(project, outputPath);

        File file = new File(outputPath);
        assertTrue(file.exists(), "Le fichier XML doit être créé.");
    }

    @Test
    void testXMLParser() {
        String xmiPath = "../UML/resources/xmlFiles/xmiFile.xmi"; 
        File file = new File(xmiPath);

        if (!file.exists()) {
            fail("Le fichier XMI à parser n'existe pas : " + xmiPath);
        }
        assertDoesNotThrow(() -> XMLParser.parse(xmiPath), "Le parsing XMI ne doit pas générer d'erreur.");
    }
}
