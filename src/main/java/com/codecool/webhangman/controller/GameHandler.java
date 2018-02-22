package com.codecool.webhangman.controller;

import com.codecool.webhangman.model.Activity;
import com.codecool.webhangman.model.GuessTable;
import com.codecool.webhangman.model.Player;
import com.codecool.webhangman.model.TemplateProcessorFacade;
import com.codecool.webhangman.service.GameLeadService;
import com.codecool.webhangman.service.GameInitializerService;
import com.codecool.webhangman.service.GameStateAnalyserService;
import com.codecool.webhangman.service.HighScoresService;
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

    public GameHandler(GameLeadService gameLeadService,
                       GameView gameView,
                       GameStateAnalyserService gameStateAnalyserService,
                       HighScoresService highScoresService,
                       GameInitializerService gameInitializerService) {

        this.gameLeadService = gameLeadService;
        this.gameView = gameView;
        this.gameStateAnalyserService = gameStateAnalyserService;
        this.highScoresService = highScoresService;
        this.gameInitService = gameInitializerService;
    }

    @GetMapping
    public String doGet(HttpServletRequest request) {
        return this.gameView.prepareGetContent(request);
    }

    @PostMapping
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Activity activity = handleTurn(request);
        GuessTable guessTable = getGuessTable(request);

        if (activity.hasPlayerWon(this.gameStateAnalyserService.getCapitalAsGuess(guessTable)) || activity.hasPlayerLost()) {
            response.sendRedirect("/hangman/end");

        } else {
            response.sendRedirect("/hangman");
        }
    }

    @GetMapping(path = "/end")
    public String getEndGame(HttpServletRequest request) throws IOException {
        Player player = getPlayer(request);

        if (player.getHealthPoints() < 1) {
            return this.gameView.getLoseView(player);
        } else {
            highScoresService.addToHighScore(player);
            return this.gameView.getWinView(player, this.highScoresService.getHighScores());
        }
    }

    @PostMapping("/end")
    public void playAgain(HttpServletRequest request, HttpServletResponse response) throws IOException {
        gameInitService.initializeRegisterPlayer(request, getPlayer(request));
        response.sendRedirect("/hangman");
    }

    @GetMapping("/rules")
    public String getRules() {
        TemplateProcessorFacade processor = new TemplateProcessorFacade("/templates/startScreen.twig");

        String contentCss = "classpath:/" + "templates/cssSettings/game-end-css-snippet.html";
        processor.modelWith("content_css", contentCss);
        String contentPath = "classpath:/" + "templates/backgroundsnippets/game-menu.twig";
        processor.modelWith("content_path", contentPath);
        contentPath = "classpath:/" + "templates/backgroundsnippets/game-rules.html";
        processor.modelWith("game_board", contentPath);

        return processor.render();
    }

    @GetMapping("/exit")
    public void exit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        response.sendRedirect("/");
    }

    private Activity handleTurn(HttpServletRequest request) {
        Player player = getPlayer(request);
        GuessTable guessTable = getGuessTable(request);
        String userGuess = request.getParameter("user-guess");
        this.gameLeadService.doNextMove(guessTable, player, userGuess);

        return new Activity(player, guessTable);
    }

    private Player getPlayer(HttpServletRequest request) {
        SessionInterpreter.RequestInterpreter requestInterpreter = SessionInterpreter.create(request);
        return requestInterpreter.retrievePlayer();
    }

    private GuessTable getGuessTable(HttpServletRequest request) {
        SessionInterpreter.RequestInterpreter requestInterpreter = SessionInterpreter.create(request);
        return requestInterpreter.retrieveGuessTable();
    }
}
