package org.mql.java.models;

import java.util.List;

public class UMLPackage {
    private String packageName;
    private List<UMLClass> classes;

    public UMLPackage(String packageName, List<UMLClass> classes) {
        this.packageName = packageName;
        this.classes = classes;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public List<UMLClass> getClasses() {
        return classes;
    }

    public void setClasses(List<UMLClass> classes) {
        this.classes = classes;
    }

    @Override
    public String toString() {
        return "UMLPackage{" +
                "packageName='" + packageName + '\'' +
                ", classes=" + classes +
                '}';
    }
}
