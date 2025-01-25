package org.mql.java.models;

import java.util.ArrayList;
import java.util.List;

public class PackageEntity {
    private String name;
    private List<ClassEntity> classes;
    private List<UMLRelation> relations; // Relations entre les classes dans le package

    public PackageEntity(String name) {
        this.name = name;
        this.classes = new ArrayList<>();
        this.relations = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ClassEntity> getClasses() {
        return classes;
    }

    public void setClasses(List<ClassEntity> classes) {
        this.classes = classes;
    }

    public void addClass(ClassEntity classEntity) {
        classes.add(classEntity);
    }

    public List<UMLRelation> getRelations() {
        return relations;
    }

    public void setRelations(List<UMLRelation> relations) {
        this.relations = relations;
    }

    public void addRelation(UMLRelation relation) {
        relations.add(relation);
    }
}