package com.codecool.webhangman.service;

import com.codecool.webhangman.model.GuessTable;
import com.codecool.webhangman.model.Player;
import org.springframework.stereotype.Service;

@Service
public class GameBoardService {
    public void handleGame(GuessTable guessTable, Player player, String userGuess) {
        if (guessTable.isAlreadyUsed(userGuess)) {
            return;
        }

        if (guessTable.doCountryNameContains(userGuess)) {
            
        } else {
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


}
