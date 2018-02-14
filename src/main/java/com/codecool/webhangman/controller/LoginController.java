package com.codecool.webhangman.controller;

import com.codecool.webhangman.model.Player;
import org.springframework.stereotype.Controller;

import javax.servlet.http.Cookie;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class LoginController {
    private static Map<UUID, Player> loggedPlayers = new ConcurrentHashMap<>();

    public void addPlayer(Cookie cookie, Player player) {
        UUID uuid = UUID.fromString(cookie.getValue());
        loggedPlayers.put(uuid, player);
    }

    public boolean isPlayerLoggedIn() {
        return false;
    }

    public void removePlayer() {
        // later
    }
}
