package com.codecool.webhangman.controller;

import com.codecool.webhangman.service.GameInitializerService;
import com.codecool.webhangman.view.LoginPageView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/")
public class LoginHandler {
    private LoginPageView loginPageView;
    private GameInitializerService gameInitializerService;

    public LoginHandler(GameInitializerService gameInitializerService,
                        LoginPageView loginPageView) {
        this.gameInitializerService = gameInitializerService;

    }

    @GetMapping
    public String getStartScreen() {
        return this.loginPageView.loadStartPageContent();
    }

    @PostMapping
    public void initializeGameWithDeliveredData(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.gameInitializerService.initializeFromRequest(request);
        response.sendRedirect("/hangman");
    }
}
