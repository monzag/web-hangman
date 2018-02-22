package com.codecool.webhangman.service.permissionsmanagementservice;

public interface WebDirectory {
    String getPath();

    default String getPathsContainerIdentity() {
        return this.getClass().getSimpleName();
    }
}
