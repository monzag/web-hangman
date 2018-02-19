package com.codecool.webhangman.model;

public class Activity {
    public final Player player;
    public final GuessTable guessTable;

    public Activity(Player player, GuessTable guessTable) {
        this.player = player;
        this.guessTable = guessTable;
    }

    public boolean hasWon(String guess) {
        if (!this.player.isAlive()) {
            return false;
        }

        Country country = this.guessTable.getCountry();
        String capital = country.getCapital().toLowerCase();
        return capital.equals(guess) ||
                this.guessTable.getUsedWords().stream()
                                              .anyMatch(usedWord -> usedWord.equals(capital));
    }

    public boolean hasLost() {
        return player.getHealthPoints() < 1;
    }
}
