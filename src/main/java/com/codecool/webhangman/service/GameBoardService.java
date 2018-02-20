package com.codecool.webhangman.service;

import com.codecool.webhangman.model.GuessTable;
import com.codecool.webhangman.model.Player;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class GameBoardService {

    private DrawService drawService;

    public GameBoardService() {
        this.drawService = new DrawService();
    }

    public void doNextMove(GuessTable guessTable, Player player, String userGuess) {
        if (guessTable.isAlreadyUsed(userGuess)) {
            return;
        }

        if (!guessTable.doCountryNameContains(userGuess)) {
            reducePlayerHealthBasedOnGuess(userGuess, player);
        }

        guessTable.collectSentenceToProperContainer(userGuess);
    }

    private void reducePlayerHealthBasedOnGuess(String guess, Player player) {
        if (guess.length() == 1) {
            player.reduceHealthPoints(1);

        } else if (guess.length() > 1) {
            player.reduceHealthPoints(2);
        }
    }

    public String getCapitalAsGuess(GuessTable guessTable) {
        String capital = guessTable.getCountry().getCapital();
        Set<String> userGuessing = guessTable.getValidLetters();
        String guess = convertToGuess(capital, userGuessing);

        return guess;
    }

    private String convertToGuess(String capital, Set<String> userGuessing) {
        StringBuilder guess = new StringBuilder();
        System.out.println(capital);
        Character letter;
        for (int i = 0; i < capital.length(); i++) {
            letter = capital.charAt(i);
            if (Character.isWhitespace(letter)) {
                guess.append("  ");
            } else {
                String letters = String.valueOf(letter);
                if (isLetterGuess(letters, userGuessing)) {
                    guess.append(letter);
                } else {
                    guess.append("_ ");
                }
            }
        }

        return guess.toString();
    }

    private boolean isLetterGuess(String letter, Set<String> userGuessing) {
        return userGuessing.contains(letter);
    }

    public String getHangmanPath(Player player) {
        return this.drawService.getDrawPath(player.getHealthPoints());

    }

    public String getHint(Player player, GuessTable guessTable) {
        String hint = "";
        if (player.getHealthPoints().equals(1)) {
            String country = guessTable.getCountry().getName();
            hint = "HINT! The capital of " + country;
        }

        return hint;
    }
}
