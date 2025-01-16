package org.mql.java.xml;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class XMLViewer {
    public static void displayXML(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
