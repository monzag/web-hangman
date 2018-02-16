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

        SessionInterpreter.RequestInterpreter requestInterpreter = SessionInterpreter.create(request);
        Player player = requestInterpreter.retrievePlayer();
        GuessTable guessTable = requestInterpreter.retrieveGuessTable();


        String contentCss = "classpath:/" + "templates/cssSettings/game-css-snippet.html";
        processor.modelWith("content_css", contentCss);
        processor.modelWith("player", player);
        processor.modelWith("guess", gameBoardService.getCapitalAsGuess(guessTable));

        String contentPath = "classpath:/" + "templates/backgroundsnippets/game-snippet.html";
        processor.modelWith("content_path", contentPath);

        return processor.render();
    }

    @PostMapping
    public String doPost(HttpServletRequest request) {
        Activity activity = handleTurn(request);




        return "LOSEE HP";
    }

    private Activity handleTurn(HttpServletRequest request) {
        SessionInterpreter.RequestInterpreter requestInterpreter = SessionInterpreter.create(request);

        Player player = requestInterpreter.retrievePlayer();
        GuessTable guessTable = requestInterpreter.retrieveGuessTable();
        String userGuess = request.getParameter("user-guess");
        this.gameBoardService.doNextMove(guessTable, player, userGuess);

        return new Activity(player, guessTable);
    }

}
