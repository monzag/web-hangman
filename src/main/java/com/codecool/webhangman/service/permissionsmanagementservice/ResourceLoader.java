package com.codecool.webhangman.service.permissionsmanagementservice;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
class ResourceLoader {
    private static final String urlPathsContainersPackage = "com.codecool.webhangman.enums";
    private Reflections reflections;

    public ResourceLoader(Reflections reflections) {
        this.reflections = reflections;
    }

    public Set<Class<?>> loadUrlPathsContainers() {
        return this.reflections.reflectPackage(urlPathsContainersPackage);
    }

    public static String getUrlPathsContainersPackage( ) {
        return urlPathsContainersPackage;
    }

    public Reflections getReflections( ) {
        return reflections;
    }

    public void setReflections(Reflections reflections) {
        this.reflections = reflections;
    }
}
