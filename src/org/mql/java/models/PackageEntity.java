package org.mql.java.models;

import java.util.ArrayList;
import java.util.List;

public class PackageEntity {
    private String name;
    private List<ClassEntity> classes;

    public PackageEntity(String name) {
        this.name = name;
        classes = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addClass(ClassEntity classEntity) {
        classes.add(classEntity);
    }

    public List<ClassEntity> getClasses() {
        return classes;
    }
}
