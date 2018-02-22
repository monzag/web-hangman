package com.codecool.webhangman.enums;

import com.codecool.webhangman.service.permissionsmanagementservice.DefaultRoute;
import com.codecool.webhangman.service.permissionsmanagementservice.UrlPaths;
import com.codecool.webhangman.service.permissionsmanagementservice.WebDirectory;

@UrlPaths
public enum LoggedInDirectoriesContainer implements WebDirectory {
    @DefaultRoute
    MAIN_HANGMAN_VIEW("/hangman"),

    GAME_END("/hangman/end"),
    GAME_EXIT("/hangman/exit"),
    GAME_RULES("/hangman/rules");

    private String path;

    LoggedInDirectoriesContainer(String path) {
        this.path = path;
    }

    @Override
    public String getPath( ) {
        return path;
    }
}
