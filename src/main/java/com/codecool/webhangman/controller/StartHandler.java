package com.codecool.webhangman.controller;

import com.codecool.webhangman.dao.PlayerDao;
import com.codecool.webhangman.model.Player;
import com.codecool.webhangman.service.CookieCreator;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/")
public class StartHandler {

    private PlayerDao playerDao;
    private LoginController loginController;

    public StartHandler(PlayerDao playerDao, LoginController loginController) {
        this.playerDao = playerDao;
        this.loginController = loginController;
    }

    @GetMapping
    public String getStartScreen(HttpServletRequest request) {

        System.out.println("Inside: startHandler");

        JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/startScreen.twig");
        JtwigModel model = JtwigModel.newModel();

        return template.render(model);
    }

    @PostMapping
    public void createPlayer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie cookie = CookieCreator.createCookie();

        response.addCookie(cookie);

        String nick = request.getParameter("nick");
        Player player = new Player(nick);

        loginController.addPlayer(cookie, player);
        response.sendRedirect("/hangman");
    }
}
