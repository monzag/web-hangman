package com.codecool.webhangman.controller;

import com.codecool.webhangman.model.TemplateProcessorFacade;
import com.codecool.webhangman.service.GameInitializerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/")
public class StartHandler {

    private GameInitializerService gameInitializerService;

    public StartHandler(GameInitializerService gameInitializerService) {
        this.gameInitializerService = gameInitializerService;
    }

    @GetMapping
    public String getStartScreen() {
        TemplateProcessorFacade processor = new TemplateProcessorFacade("/templates/startScreen.twig");

        String contentCss = "classpath:/" + "templates/cssSettings/login-css-snippet.html";
        processor.modelWith("content_css", contentCss);

        String contentPath = "classpath:/" + "templates/backgroundsnippets/start-screen-snippet.html";
        processor.modelWith("content_path", contentPath);

        return processor.render();
    }

    @PostMapping
    public void initializeGameWithDeliveredData(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.gameInitializerService.initializeFromRequest(request);
        response.sendRedirect("/hangman");
    }
}
