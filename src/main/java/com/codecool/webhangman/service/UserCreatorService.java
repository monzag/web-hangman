package com.codecool.webhangman.service;

import com.codecool.webhangman.enums.Identity;
import com.codecool.webhangman.model.Player;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class UserCreatorService {

    public Player createPlayer(HttpServletRequest request) {
        String nick = request.getParameter("nick");
        return new Player(nick);
    }

//    public Player refreshPlayer(String nick) {
//        return new Player(nick);
//    }

    public void registerUserInSession(Player player, HttpServletRequest request) {
        HttpSession session = request.getSession();

        session.setAttribute(Identity.PLAYER.getKey(), player);
        session.setAttribute(Identity.IS_LOGGED_IN.getKey(), true);
    }
}
