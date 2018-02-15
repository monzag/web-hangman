package com.codecool.webhangman.controller;

import com.codecool.webhangman.enums.Identity;
import com.codecool.webhangman.model.GuessTable;
import com.codecool.webhangman.model.Player;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionInterpreter {

    public static class RequestInterpreter {
        private HttpServletRequest request;

        private RequestInterpreter(HttpServletRequest request) {
            this.request = request;
        }

        public boolean isUserLoggedIn() {
            HttpSession httpSession = this.request.getSession();
            final String key = Identity.IS_LOGGED_IN.getKey();

            Object obj = httpSession.getAttribute(key);

            if (obj == null) {
                return false;
            }

            if (!(obj instanceof Boolean)) {
                throw new IllegalArgumentException("IsLoggedIn attr should be Boolean");
            }

            return obj.equals(true);
        }

        public Player retrievePlayer() {
            final String key = Identity.PLAYER.getKey();
            return (Player) this.request.getAttribute(key);
        }

        public GuessTable retrieveGuessTable() {
            final String key = Identity.GUESS_TABLE.getKey();
            return (GuessTable) this.request.getAttribute(key);
        }
    }

    public static RequestInterpreter create(HttpServletRequest request) {
           return new RequestInterpreter(request);
    }
}
