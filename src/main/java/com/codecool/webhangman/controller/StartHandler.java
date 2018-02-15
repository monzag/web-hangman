package com.codecool.webhangman.controller;

import com.codecool.webhangman.dao.PlayerDao;
import com.codecool.webhangman.model.Player;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequestMapping("/")
public class StartHandler {

    private PlayerDao playerDao;

    public StartHandler(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    @GetMapping
    public String getStartScreen(HttpServletResponse response, HttpServletRequest request) throws IOException {

        JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/startScreen.twig");
        JtwigModel model = JtwigModel.newModel();

        return template.render(model);
    }

    @PostMapping
    public void createPlayer(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String nick = request.getParameter("nick");
        Player player = new Player(nick);
        HttpSession session = request.getSession();

        session.setAttribute("player", player);
        session.setAttribute("isLoggedIn", true);

        response.sendRedirect("/hangman");
    }
}
