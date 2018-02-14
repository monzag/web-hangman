package com.codecool.webhangman.controller;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HangmanHandler {


    @GetMapping
    public String getStartScreen() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/startScreen.twig");
        JtwigModel model = JtwigModel.newModel();

        return template.render(model);
    }
}
