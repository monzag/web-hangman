package com.codecool.webhangman.controller;

import com.codecool.webhangman.model.Player;
import com.codecool.webhangman.model.PlayerActivity;
import com.codecool.webhangman.service.*;
import com.codecool.webhangman.view.GameView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/hangman")
public class GameHandler {
    private GameLeadService gameLeadService;
    private GameView gameView;
    private GameStateAnalyserService gameStateAnalyserService;
    private HighScoresService highScoresService;
    private GameInitializerService gameInitService;
    private SessionInterpreterFacade sessionInterpreterFacade;

    public GameHandler(GameLeadService gameLeadService,
                       GameView gameView,
                       GameStateAnalyserService gameStateAnalyserService,
                       HighScoresService highScoresService,
                       GameInitializerService gameInitializerService,
                       SessionInterpreterFacade sessionInterpreterFacade) {

        this.gameLeadService = gameLeadService;
        this.gameView = gameView;
        this.gameStateAnalyserService = gameStateAnalyserService;
        this.highScoresService = highScoresService;
        this.gameInitService = gameInitializerService;
        this.sessionInterpreterFacade = sessionInterpreterFacade;
    }

    @GetMapping
    public String doGet(HttpServletRequest request) {
        PlayerActivity playerActivity = this.sessionInterpreterFacade.getPlayerActivity(request);
        return this.gameView.prepareGetContent(playerActivity);
    }

    @PostMapping
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PlayerActivity playerActivity = handleTurn(request);
        String xxx = this.gameStateAnalyserService.getCapitalAsGuess(playerActivity.getGuessTable());

        if (playerActivity.hasPlayerWon(xxx) || playerActivity.hasPlayerLost()) {
            response.sendRedirect("/hangman/end");

        } else {
            response.sendRedirect("/hangman");
        }
    }

    private PlayerActivity handleTurn(HttpServletRequest request) {
        PlayerActivity currentActivity = this.sessionInterpreterFacade.getPlayerActivity(request);
        String userGuess = request.getParameter("user-guess");
        this.gameLeadService.doNextMove(currentActivity, userGuess);
        return currentActivity;
    }


    @GetMapping(path = "/end")
    public String getEndGame(HttpServletRequest request) throws IOException {
        Player player = this.sessionInterpreterFacade.getPlayer(request);

        if (player.getHealthPoints() < 1) {
            return this.gameView.getLoseView(player, this.highScoresService.getHighScores());
        } else {
            highScoresService.addToHighScore(player);
            return this.gameView.getWinView(player, this.highScoresService.getHighScores());
        }
    }

    @PostMapping("/end")
    public void playAgain(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Player player = this.sessionInterpreterFacade.getPlayer(request);
        gameInitService.initializeRegisterPlayer(request, player);
        response.sendRedirect("/hangman");
    }

    @GetMapping("/rules")
    public String getRules() {
        return this.gameView.getRulesPageContent();
    }

    @GetMapping("/exit")
    public void exit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        response.sendRedirect("/");
    }
}
