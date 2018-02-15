package com.codecool.webhangman.model;

import java.util.Set;

public class Activity {
    public final Player player;
    public final GuessTable guessTable;

    public Activity(Player player, GuessTable guessTable) {
        this.player = player;
        this.guessTable = guessTable;
    }

    public boolean hasWon() {
        if (!this.player.isAlive()) {
            return false;
        }

        Country country = this.guessTable.getCountry();
        String capital = country.getCapital().toLowerCase();

        Set<String> validLetters = this.guessTable.getValidLetters();
        return validLetters.size() == capital.length() ||
                this.guessTable.getUsedWords().stream()
                                              .anyMatch(usedWord -> usedWord.equals(capital));

    }
}
