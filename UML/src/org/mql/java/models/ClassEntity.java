package org.mql.java.models;

import java.util.ArrayList;
import java.util.List;

public class ClassEntity {
    private String name;
    private boolean isInterface;
    private boolean isEnum;
    private List<String> annotations;
    private List<String> enumConstants;
    private List<String> fields;
    private List<String> methods;
    private List<UMLRelation> relations; // Ajout des relations UML

    public ClassEntity(String name) {
        this.name = name;
        this.annotations = new ArrayList<>();
        this.fields = new ArrayList<>();
        this.methods = new ArrayList<>();
        this.relations = new ArrayList<>(); // Initialisation des relations
    }

    public String getName() {
        return name;
    }

    public void setInterface(boolean isInterface) {
        this.isInterface = isInterface;
    }

    public boolean isInterface() {
        return isInterface;
    }

    public void setEnum(boolean isEnum) {
        this.isEnum = isEnum;
    }

    public boolean isEnum() {
        return isEnum;
    }

    public void addAnnotation(String annotation) {
        annotations.add(annotation);
    }

    public List<String> getAnnotations() {
        return annotations;
    }

    public void setEnumConstants(List<String> enumConstants) {
        this.enumConstants = enumConstants;
    }

    public List<String> getEnumConstants() {
        return enumConstants;
    }

    public void addField(String field) {
        fields.add(field);
    }

    public List<String> getFields() {
        return fields;
    }

    public void addMethod(String method) {
        methods.add(method);
    }

    public List<String> getMethods() {
        return methods;
    }

    public void addRelation(UMLRelation relation) {
        relations.add(relation);
    }

    public List<UMLRelation> getRelations() {
        return relations;
    }
}
