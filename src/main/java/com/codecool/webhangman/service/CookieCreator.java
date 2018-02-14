package com.codecool.webhangman.service;

import javax.servlet.http.Cookie;
import java.util.UUID;

public class CookieCreator {
    public static Cookie createCookie() {
        UUID uuid = UUID.randomUUID();
        Cookie cookie = new Cookie("PlayerUUID", uuid.toString());

        cookie.setPath("/hangman");
        return cookie;
    }
}
