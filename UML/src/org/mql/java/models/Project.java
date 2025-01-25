package org.mql.java.models;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private List<PackageEntity> packages;

    public Project() {
        this.packages = new ArrayList<>();
    }

    public List<PackageEntity> getPackages() {
        return packages;
    }

    public void addPackage(PackageEntity packageEntity) {
        packages.add(packageEntity);
    }
}