package com.codecool.webhangman.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/hangman")
public class GameHandler {

    @GetMapping
    public String doGet(HttpServletRequest request) {

        System.out.println("Inside: GameHandler");
        return "HELLO";
    }
}
