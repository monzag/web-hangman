package com.codecool.webhangman.controller;

import com.codecool.webhangman.model.Activity;
import com.codecool.webhangman.model.GuessTable;
import com.codecool.webhangman.model.Player;
import com.codecool.webhangman.model.TemplateProcessorFacade;
import com.codecool.webhangman.service.GameBoardService;
import com.codecool.webhangman.service.GameInitializerService;
import com.codecool.webhangman.service.HighscoreService;
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
    private GameBoardService gameBoardService;
    private HighscoreService highscoreService;

    public GameHandler(GameBoardService gameBoardService, HighscoreService highscoreService) {

        this.gameBoardService = gameBoardService;
        this.highscoreService = highscoreService;
    }

    @GetMapping
    public String doGet(HttpServletRequest request) {
        TemplateProcessorFacade processor = new TemplateProcessorFacade("/templates/startScreen.twig");
        Player player = getPlayer(request);
        GuessTable guessTable = getGuessTable(request);
        String path = gameBoardService.getHangmanPath(player);
        String guess = gameBoardService.getCapitalAsGuess(guessTable);

        String contentCss = "classpath:/" + "templates/cssSettings/game-css-snippet.html";
        processor.modelWith("content_css", contentCss);
        processor.modelWith("player", player);
        processor.modelWith("guess", guess);
        processor.modelWith("photo_src", path);
        processor.modelWith("guessTable", guessTable);

        String contentPath = "classpath:/" + "templates/backgroundsnippets/game-menu.twig";
        processor.modelWith("content_path", contentPath);
        contentPath = "classpath:/" + "templates/backgroundsnippets/game-board.html";
        processor.modelWith("game_board", contentPath);

        return processor.render();
    }

    @PostMapping
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Activity activity = handleTurn(request);
        GuessTable guessTable = getGuessTable(request);

        if (activity.hasWon(gameBoardService.getCapitalAsGuess(guessTable)) || activity.hasLost()) {
            response.sendRedirect("/hangman/end");

        } else {
            response.sendRedirect("/hangman");
        }
    }

    @GetMapping(path = "/end")
    public String getEndGame(HttpServletRequest request) {
        TemplateProcessorFacade processor = new TemplateProcessorFacade("/templates/startScreen.twig");

        Player player = getPlayer(request);
        String contentCss = "classpath:/" + "templates/cssSettings/game-end-css-snippet.html";
        processor.modelWith("content_css", contentCss);
        String contentPath = "classpath:/" + "templates/backgroundsnippets/game-menu.twig";
        processor.modelWith("content_path", contentPath);
        contentPath = "classpath:/" + "templates/backgroundsnippets/game-end.html";
        processor.modelWith("game_board", contentPath);

        String resultText;
        if (player.getHealthPoints() < 1) {
            resultText = player.getNick() + " - you lose :(";
        } else {
            highscoreService.addToHighscore(player);
            resultText = "Congratulation " + player.getNick() + " - you are winner!!";
        }

        processor.modelWith("result_text", resultText);
        processor.modelWith("highscore", highscoreService.getHighscore());

        return processor.render();
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
        this.gameBoardService.doNextMove(guessTable, player, userGuess);

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
