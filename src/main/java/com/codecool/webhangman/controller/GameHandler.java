package com.codecool.webhangman.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequestMapping("/hangman")
public class GameHandler {

    @GetMapping
    public String doGet(HttpServletResponse response, HttpServletRequest request) {
        TemplateProcessorFacade processor = new TemplateProcessorFacade("/templates/startScreen.twig");

        String contentPath = "classpath:/" + "templates/backgroundsnippets/game-snippet.html";
        processor.modelWith("content_path", contentPath);

        return processor.render();
    }


    public String doPost(HttpServletResponse response, HttpServletRequest request) {

//
//
//        String contentPath = "classpath:/" + managerOption.getPath();
//        model.with("content_path", contentPath);



        return null;
    }
}
