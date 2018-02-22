package com.codecool.webhangman.model;

public class PlayerActivity {
    private final Player player;
    private final GuessTable guessTable;

    public PlayerActivity(Player player, GuessTable guessTable) {
        this.player = player;
        this.guessTable = guessTable;
    }

    public boolean hasPlayerWon(String guess) {
        if (!this.player.isAlive()) {
            return false;
        }

        guess = guess.replaceAll("  ", " ");
        Country country = this.guessTable.getCountry();
        String capital = country.getCapital().toLowerCase();
        return capital.equals(guess) ||
                this.guessTable.getUsedWords().stream()
                                              .anyMatch(usedWord -> usedWord.equals(capital));
    }

    public boolean hasPlayerLost() {
        return player.getHealthPoints() < 1;
    }

    public Player getPlayer( ) {
        return player;
    }

    public GuessTable getGuessTable( ) {
        return guessTable;
    }
}
