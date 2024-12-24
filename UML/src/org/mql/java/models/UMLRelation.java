package org.mql.java.models;

public class UMLRelation {
    private String type; 
    private ClassEntity source; 
    private ClassEntity target;

    public UMLRelation(String type, ClassEntity source, ClassEntity target) {
        this.type = type;
        this.source = source;
        this.target = target;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ClassEntity getSource() {
        return source;
    }

    public void setSource(ClassEntity source) {
        this.source = source;
    }

    public ClassEntity getTarget() {
        return target;
    }

    public void setTarget(ClassEntity target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "UMLRelation{" +
                "type='" + type + '\'' +
                ", source=" + source +
                ", target=" + target +
                '}';
    }
}
