package com.codecool.webhangman.enums;

public enum Identity {
    PLAYER,
    GUESS_TABLE,
    IS_LOGGED_IN;

    private String name;

    Identity() {
        this.name = this.toString();
    }

    public String getKey() {
        return this.name;
    }
}
