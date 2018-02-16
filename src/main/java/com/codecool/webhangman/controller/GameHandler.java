package com.codecool.webhangman.controller;

import com.codecool.webhangman.model.Activity;
import com.codecool.webhangman.model.GuessTable;
import com.codecool.webhangman.model.Player;
import com.codecool.webhangman.model.TemplateProcessorFacade;
import com.codecool.webhangman.service.GameBoardService;
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

    public GameHandler(GameBoardService gameBoardService) {
        this.gameBoardService = gameBoardService;
    }

    @GetMapping
    public String doGet(HttpServletResponse response, HttpServletRequest request) {
        TemplateProcessorFacade processor = new TemplateProcessorFacade("/templates/startScreen.twig");

        Player player = getPlayer(request);
        GuessTable guessTable = requestInterpreter.retrieveGuessTable();
        String guess = gameBoardService.getCapitalAsGuess(guessTable);
        String path = gameBoardService.getHangmanPath(player);

        String contentCss = "classpath:/" + "templates/cssSettings/game-css-snippet.html";
        processor.modelWith("content_css", contentCss);
        processor.modelWith("player", player);
        processor.modelWith("guess", guess);
        processor.modelWith("photo_src", path);
        processor.modelWith("guessTable", guessTable);

        String contentPath = "classpath:/" + "templates/backgroundsnippets/game-snippet.html";
        processor.modelWith("content_path", contentPath);

        return processor.render();
    }

    @PostMapping
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Activity activity = handleTurn(request);

        if (activity.hasWon()) {

        } else {
            response.sendRedirect("/hangman");
        }
    }

//    @GetMapping(path = "/win")
//    public String doGet() {
//
//    }

    private Activity handleTurn(HttpServletRequest request) {
        Player player = getPlayer(request);
        GuessTable guessTable = requestInterpreter.retrieveGuessTable();
        String userGuess = request.getParameter("user-guess");
        this.gameBoardService.doNextMove(guessTable, player, userGuess);

        return new Activity(player, guessTable);
    }

    private Player getPlayer(HttpServletRequest request) {
        SessionInterpreter.RequestInterpreter requestInterpreter = SessionInterpreter.create(request);
        return requestInterpreter.retrievePlayer();
    }

}
