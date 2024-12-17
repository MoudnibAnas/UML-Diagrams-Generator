package org.mql.java.models;

import java.util.ArrayList;
import java.util.List;

public class ClassEntity {
    private String name;
    private boolean isInterface;
    private boolean isEnum;
    private List<String> annotations;

    public ClassEntity(String name) {
        this.name = name;
        annotations = new ArrayList<>();
    }

    public void setInterface(boolean isInterface) {
        this.isInterface = isInterface;
    }

    public void setEnum(boolean isEnum) {
        this.isEnum = isEnum;
    }

    public void addAnnotation(String annotation) {
        annotations.add(annotation);
    }

    public String getName() {
        return name;
    }

    public boolean isInterface() {
        return isInterface;
    }

    public boolean isEnum() {
        return isEnum;
    }

    public List<String> getAnnotations() {
        return annotations;
    }
}
