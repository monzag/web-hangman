package com.codecool.webhangman.service.permissionsmanagementservice;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class LoggedInAccessPeeper {
    private static final String containerName = "LoggedInDirectoriesContainer";
    private StaticPermissionsDataContainer staticPermissionsDataContainer;

    public LoggedInAccessPeeper(StaticPermissionsDataContainer staticPermissionsDataContainer) {
        this.staticPermissionsDataContainer = staticPermissionsDataContainer;

        Set<String> validKeys = staticPermissionsDataContainer.getKeySet();
        if (!validKeys.contains(containerName)) {
            String message = "Invalid state, valid keys don't contains (this container | -> " + containerName;
            throw new IllegalStateException(message);
        }
    }

    public boolean contains(String path) {
        return this.staticPermissionsDataContainer.contains(containerName, path);
    }

    public String getDefaultPath() {
        return this.staticPermissionsDataContainer.getDefaultPath(containerName);
    }

    public static String getContainerName( ) {
        return containerName;
    }

    public StaticPermissionsDataContainer getStaticPermissionsDataContainer( ) {
        return staticPermissionsDataContainer;
    }

    public void setStaticPermissionsDataContainer(StaticPermissionsDataContainer staticPermissionsDataContainer) {
        this.staticPermissionsDataContainer = staticPermissionsDataContainer;
    }
}
