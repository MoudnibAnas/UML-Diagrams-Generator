package org.mql.java.models;

import java.util.List;

public class UMLInterface {
    private String interfaceName;
    private List<String> methods;

    public UMLInterface(String interfaceName, List<String> methods) {
        this.interfaceName = interfaceName;
        this.methods = methods;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public List<String> getMethods() {
        return methods;
    }

    public void setMethods(List<String> methods) {
        this.methods = methods;
    }

    @Override
    public String toString() {
        return "UMLInterface{" +
                "interfaceName='" + interfaceName + '\'' +
                ", methods=" + methods +
                '}';
    }
}
