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

	public ClassEntity(String name) {
		this.name = name;
		annotations = new ArrayList<>();
		fields = new ArrayList<>();
		methods = new ArrayList<>();
	}

	public void setEnumConstants(List<String> enumConstants) {
		this.enumConstants = enumConstants;
	}

	public List<String> getEnumConstants() {
		return enumConstants;
	}

	public void setInterface(boolean isInterface) {
		this.isInterface = isInterface;
	}

	public List<String> getAnnotations() {
		return annotations;
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

	public void setEnum(boolean isEnum) {
		this.isEnum = isEnum;
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
}
