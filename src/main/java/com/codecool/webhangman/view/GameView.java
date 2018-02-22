package com.codecool.webhangman.view;

import com.codecool.webhangman.controller.SessionInterpreter;
import com.codecool.webhangman.model.GuessTable;
import com.codecool.webhangman.model.Player;
import com.codecool.webhangman.model.Score;
import com.codecool.webhangman.model.TemplateProcessorFacade;
import com.codecool.webhangman.service.GameStateAnalyserService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class GameView {
    private GameStateAnalyserService gameStateAnalyserService;

    public GameView(GameStateAnalyserService gameStateAnalyserService) {
        this.gameStateAnalyserService = gameStateAnalyserService;
    }

    public String prepareGetContent(HttpServletRequest request) {
        TemplateProcessorFacade processor = new TemplateProcessorFacade("/templates/startScreen.twig");
        Player player = getPlayer(request);
        GuessTable guessTable = getGuessTable(request);
        String path = this.gameStateAnalyserService.getHangmanPath(player);
        String guess = this.gameStateAnalyserService.getCapitalAsGuess(guessTable);

        String contentCss = "classpath:/" + "templates/cssSettings/game-css-snippet.html";
        processor.modelWith("content_css", contentCss);
        processor.modelWith("player", player);
        processor.modelWith("guess", guess);
        processor.modelWith("photo_src", path);
        processor.modelWith("guessTable", guessTable);
        processor.modelWith("hint", this.gameStateAnalyserService.getHint(player, guessTable));

        String contentPath = "classpath:/" + "templates/backgroundsnippets/game-menu.twig";
        processor.modelWith("content_path", contentPath);
        contentPath = "classpath:/" + "templates/backgroundsnippets/game-board.html";
        processor.modelWith("game_board", contentPath);

        return processor.render();
    }

    private Player getPlayer(HttpServletRequest request) {
        SessionInterpreter.RequestInterpreter requestInterpreter = SessionInterpreter.create(request);
        return requestInterpreter.retrievePlayer();
    }

    private GuessTable getGuessTable(HttpServletRequest request) {
        SessionInterpreter.RequestInterpreter requestInterpreter = SessionInterpreter.create(request);
        return requestInterpreter.retrieveGuessTable();
    }

    public String getWinView(Player player, List<Score> scoreList) {
        TemplateProcessorFacade processor = gameEndProcessor();

        processor.modelWith("result_text", "Congratulation " + player.getNick() + " - you are winner!!");
        processor.modelWith("highscore", scoreList);

        return processor.render();
    }

    public String getLoseView(Player player) {
        TemplateProcessorFacade processor = gameEndProcessor();
        processor.modelWith("result_text", player.getNick() + " - you lose :(");

        return processor.render();
    }

    private TemplateProcessorFacade gameEndProcessor() {
        TemplateProcessorFacade processor = new TemplateProcessorFacade("/templates/startScreen.twig");
        String contentCss = "classpath:/" + "templates/cssSettings/game-end-css-snippet.html";
        processor.modelWith("content_css", contentCss);
        String contentPath = "classpath:/" + "templates/backgroundsnippets/game-end.html";
        processor.modelWith("content_path", contentPath);

        return processor;
    }
}
