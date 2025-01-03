package org.mql.java.models;

import java.util.ArrayList;
import java.util.List;

public class PackageEntity {
    private String name;
    private List<ClassEntity> classes;
    private List<UMLRelation> relations;
    
    public PackageEntity(String name) {
        this.name = name;
        classes = new ArrayList<>();
        relations =new ArrayList<>();
    }


    public List<UMLRelation> getRelations() {
		return relations;
	}

    
    public void addClass(ClassEntity classEntity) {
        classes.add(classEntity);
    }
    public void addRelation(UMLRelation relation) { 
        relations.add(relation);
    }
    
    
	public void setRelations(List<UMLRelation> relations) {
		this.relations = relations;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setClasses(List<ClassEntity> classes) {
		this.classes = classes;
	}

    public String getName() {
        return name;
    }
    
    public List<ClassEntity> getClasses() {
        return classes;
    }
}
