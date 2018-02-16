package com.codecool.webhangman.controller;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.function.Function;

public class SessionManager implements HandlerInterceptor{

    // This method is called before the controller
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        SessionInterpreter.RequestInterpreter requestInterpreter = SessionInterpreter.create(request);
        boolean isUserLoggedIn = requestInterpreter.isUserLoggedIn();
        String currentPath = request.getServletPath();

        Function<String, Boolean> isUserAtGameUrl = p -> p.equals("/hangman");
        Function<String, Boolean> isUserWinOrLose = p -> p.equals("/hangman/win");
        Function<String, Boolean> isUserAtLoginUrl = p -> p.equals("/");

        if (isUserLoggedIn && (!isUserAtGameUrl.apply(currentPath) && !isUserWinOrLose.apply(currentPath))) {
            response.sendRedirect("/hangman");
            return false;

        } else if (!isUserLoggedIn && !isUserAtLoginUrl.apply(currentPath)) {
            response.sendRedirect("/");
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }
}