package com.codecool.webhangman.service;

import com.codecool.webhangman.model.GuessTable;
import com.codecool.webhangman.model.Player;
import com.codecool.webhangman.model.PlayerActivity;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class GameLeadService {

    public void doNextMove(PlayerActivity playerActivity, String userGuess) {
        GuessTable guessTable = playerActivity.getGuessTable();

        if (guessTable.isAlreadyUsed(userGuess)) {
            return;
        }

        if (!guessTable.doCountryNameContains(userGuess)) {
            Player player = playerActivity.getPlayer();
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
