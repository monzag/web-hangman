package com.codecool.webhangman.service;

import com.codecool.webhangman.model.GuessTable;
import com.codecool.webhangman.model.Player;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class GameBoardService {
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
        String guess = "";
        Character letter;
        for (int i = 0; i < capital.length(); i++) {
            letter = capital.charAt(i);
            if (letter != ' ') {
                String letters = String.valueOf(letter);
                if (isLetterGuess(letters, userGuessing)) {
                    guess += letters;
                } else {
                    guess += "_ ";
                }
            } else {
                    guess += "  ";
            }
        }

        return guess;
    }

}
