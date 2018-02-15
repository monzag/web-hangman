package com.codecool.webhangman.controller;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
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
    public String doGet(HttpServletResponse response, HttpSession session) throws IOException {

        if (session.getAttribute("isLoggedIn") != null &&
                session.getAttribute("isLoggedIn").equals(true)) {
            return getPage();
        } else {
            response.sendRedirect("/");
        }


        return "You must log in!";
    }

    private String getPage() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/game.twig");
        JtwigModel model = JtwigModel.newModel();

        return template.render(model);
    }
}
