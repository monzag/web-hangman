package com.codecool.webhangman.model;

public class Activity {
    public final Player player;
    public final GuessTable guessTable;

    public Activity(Player player, GuessTable guessTable) {
        this.player = player;
        this.guessTable = guessTable;
    }
}
