package org.mql.java.ui;

import org.mql.java.models.PackageEntity;
import org.mql.java.models.Project;
import org.mql.java.service.PackageExplorer;

import javax.swing.*;
import java.awt.*;

public class UmlDiagramApp extends JFrame {

    private static final long serialVersionUID = 1L;

    public UmlDiagramApp(String rootPackageName) {
        setTitle("UML Diagram Generator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 800);

        Project project = new Project();
        PackageExplorer.scan(rootPackageName, project);

        // JTabbedPane pour afficher un diagramme par package
        JTabbedPane tabbedPane = new JTabbedPane();

        for (PackageEntity pkg : project.getPackages()) {
        	DiagramGen diagramPanel = new DiagramGen(pkg.getName(), pkg.getClasses());
            JScrollPane scrollPane = new JScrollPane(diagramPanel);
            tabbedPane.addTab(pkg.getName(), scrollPane);
        }

        add(tabbedPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String rootPackage = "org.mql.java.models";
            UmlDiagramApp app = new UmlDiagramApp(rootPackage);
            app.setVisible(true);
        });
    }
}