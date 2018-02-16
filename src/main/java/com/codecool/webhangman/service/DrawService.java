package com.codecool.webhangman.service;

import java.util.HashMap;
import java.util.Map;

public class DrawService {

    private Map<Integer, String> pictures;

    public DrawService() {
        this.pictures = new HashMap<>();
        addPictures();
    }

    public String getDrawPath(Integer healthPoints) {
        return pictures.get(healthPoints);
    }

    public void addPictures() {
        this.pictures.put(0, "../assets/pics/hangman0hp.png");
        this.pictures.put(1, "../assets/pics/hangman1hp.png");
        this.pictures.put(2, "../assets/pics/hangman2hp.png");
        this.pictures.put(3, "../assets/pics/hangman3hp.png");
        this.pictures.put(4, "../assets/pics/hangman4hp.png");
        this.pictures.put(5, "../assets/pics/hangman5hp.png");
        this.pictures.put(6, "../assets/pics/hangman6hp.png");
        this.pictures.put(7, "assets/pics/hangman7hp.png");
    }
}


