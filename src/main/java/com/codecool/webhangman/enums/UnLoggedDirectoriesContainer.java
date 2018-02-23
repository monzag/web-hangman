package com.codecool.webhangman.enums;

import com.codecool.webhangman.service.permissionsmanagementservice.DefaultRoute;
import com.codecool.webhangman.service.permissionsmanagementservice.UrlPaths;
import com.codecool.webhangman.service.permissionsmanagementservice.WebDirectory;

@UrlPaths
public enum UnLoggedDirectoriesContainer implements WebDirectory {
    @DefaultRoute
    LOGIN_PAGE("/");

    private String path;

    UnLoggedDirectoriesContainer(String path) {
        this.path = path;
    }

    @Override
    public String getPath( ) {
        return path;
    }
}
