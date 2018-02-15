package com.codecool.webhangman.controller;

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
            final String key = "isLoggedIn";

            Object obj = httpSession.getAttribute(key);

            if (obj == null) {
                return false;
            }

            if (!(obj instanceof Boolean)) {
                throw new IllegalArgumentException("IsLoggedIn attr should be Boolean");
            }

            return obj.equals(true);
        }
    }

    public static RequestInterpreter create(HttpServletRequest request) {
           return new RequestInterpreter(request);
    }
}
