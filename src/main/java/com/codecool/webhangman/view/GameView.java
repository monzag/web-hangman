package com.codecool.webhangman.view;

import com.codecool.webhangman.model.GuessTable;
import com.codecool.webhangman.model.Player;
import com.codecool.webhangman.model.PlayerActivity;
import com.codecool.webhangman.model.Score;
import com.codecool.webhangman.service.GameStateAnalyserService;
import com.codecool.webhangman.service.TemplateProcessorFacade;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameView {
    private GameStateAnalyserService gameStateAnalyserService;

    public GameView(GameStateAnalyserService gameStateAnalyserService) {
        this.gameStateAnalyserService = gameStateAnalyserService;
    }

    public String prepareGetContent(PlayerActivity playerActivity) {
        TemplateProcessorFacade processor = new TemplateProcessorFacade("/templates/startScreen.twig");
        Player player = playerActivity.getPlayer();
        GuessTable guessTable = playerActivity.getGuessTable();
        String path = this.gameStateAnalyserService.getHangmanPath(player);
        String guess = this.gameStateAnalyserService.getCapitalAsGuess(guessTable);
        Long gameTime = this.gameStateAnalyserService.getCurrentGameTime(player);

        String contentCss = "classpath:/" + "templates/cssSettings/game-css-snippet.html";
        processor.modelWith("content_css", contentCss);
        processor.modelWith("player", player);
        processor.modelWith("guess", guess);
        processor.modelWith("photo_src", path);
        processor.modelWith("guessTable", guessTable);
        processor.modelWith("startCount", (gameTime));
        processor.modelWith("hint", this.gameStateAnalyserService.getHint(player, guessTable));

        String contentPath = "classpath:/" + "templates/backgroundsnippets/game-menu.twig";
        processor.modelWith("content_path", contentPath);
        contentPath = "classpath:/" + "templates/backgroundsnippets/game-board.html";
        processor.modelWith("game_board", contentPath);

        return processor.render();
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

    public String getRulesPageContent() {
        TemplateProcessorFacade processor = new TemplateProcessorFacade("/templates/startScreen.twig");

        String contentCss = "classpath:/" + "templates/cssSettings/game-end-css-snippet.html";
        processor.modelWith("content_css", contentCss);
        String contentPath = "classpath:/" + "templates/backgroundsnippets/game-menu.twig";
        processor.modelWith("content_path", contentPath);
        contentPath = "classpath:/" + "templates/backgroundsnippets/game-rules.html";
        processor.modelWith("game_board", contentPath);

        return processor.render();
    }
}
