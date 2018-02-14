package com.codecool.webhangman.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/hangman")
public class HangmanHandler {


    @GetMapping
    public void doGet() {

    }
}
