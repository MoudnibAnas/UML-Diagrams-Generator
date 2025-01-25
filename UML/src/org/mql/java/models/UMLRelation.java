package org.mql.java.models;

import org.mql.java.enumeration.RelationType;

public class UMLRelation {
   

    private RelationType type; // Type de la relation
    private ClassEntity source; // Classe source
    private ClassEntity target; // Classe cible

    public UMLRelation(RelationType type, ClassEntity source, ClassEntity target) {
        if (source == null || target == null) {
            throw new IllegalArgumentException("Source and target classes cannot be null.");
        }
        this.type = type;
        this.source = source;
        this.target = target;
    }

    // Retourne le type de relation
    public RelationType getType() {
        return type;
    }

    public void setType(RelationType type) {
        this.type = type;
    }

    // Retourne la classe source
    public ClassEntity getSource() {
        return source;
    }

    public void setSource(ClassEntity source) {
        this.source = source;
    }

    // Retourne la classe cible
    public ClassEntity getTarget() {
        return target;
    }

    public void setTarget(ClassEntity target) {
        this.target = target;
    }

    // Retourne le nom de la classe source
    public String getFrom() {
        return source.getName();
    }

    // Retourne le nom de la classe cible
    public String getTo() {
        return target.getName();
    }

    // Méthodes utilitaires pour vérifier le type de relation
    public boolean isInheritance() {
        return type == RelationType.INHERITANCE;
    }

    public boolean isAssociation() {
        return type == RelationType.ASSOCIATION;
    }

    public boolean isImplementation() {
        return type == RelationType.IMPLEMENTATION;
    }

    public boolean isAggregation() {
        return type == RelationType.AGGREGATION;
    }

    public boolean isComposition() {
        return type == RelationType.COMPOSITION;
    }

    @Override
    public String toString() {
        return "UMLRelation{" +
                "type=" + type +
                ", source=" + source.getName() +
                ", target=" + target.getName() +
                '}';
    }
}
