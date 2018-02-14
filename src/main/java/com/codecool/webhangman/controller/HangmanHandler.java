package com.codecool.webhangman.controller;

import com.codecool.webhangman.dao.PlayerDao;
import com.codecool.webhangman.model.Player;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/")
public class HangmanHandler {

    private PlayerDao playerDao;

    public HangmanHandler(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    @GetMapping
    public String getStartScreen() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/startScreen.twig");
        JtwigModel model = JtwigModel.newModel();

        return template.render(model);
    }

    @PostMapping
    public void createPlayer(HttpServletRequest request) {
        String nick = request.getParameter("nick");
        Player player = new Player(nick);

    }
}
