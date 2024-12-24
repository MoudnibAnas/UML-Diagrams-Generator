package org.mql.java.models;

import java.util.List;


public class UMLClass {
    private String className;
    private List<String> annotations;
    private List<String> methods;
    private List<UMLRelation> relations;

    public UMLClass(String className, List<String> annotations, List<String> methods, List<UMLRelation> relations) {
        this.className = className;
        this.annotations = annotations;
        this.methods = methods;
        this.relations = relations;
    }


    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<String> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<String> annotations) {
        this.annotations = annotations;
    }

    public List<String> getMethods() {
        return methods;
    }

    public void setMethods(List<String> methods) {
        this.methods = methods;
    }

    public List<UMLRelation> getRelations() {
        return relations;
    }

    public void setRelations(List<UMLRelation> relations) {
        this.relations = relations;
    }

    @Override
    public String toString() {
        return "UMLClass{" +
                "className='" + className + '\'' +
                ", annotations=" + annotations +
                ", methods=" + methods +
                ", relations=" + relations +
                '}';
    }
}
