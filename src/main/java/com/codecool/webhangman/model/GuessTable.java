package com.codecool.webhangman.model;

import java.util.HashSet;
import java.util.Set;

public class GuessTable {
    private Country country;
    private Set<String> validLetters;
    private Set<String> usedLetters;
    private Set<String> usedWords;

    {
        this.usedLetters = new HashSet<>();
        this.usedWords = new HashSet<>();
        this.validLetters = new HashSet<>();
    }

    public GuessTable(Country country) {
        this.country = country;
    }

    public boolean doCountryNameContains(String guess) {
        String capitalName = this.country.getCapital().toLowerCase();
        System.out.println(capitalName);
        guess = guess.toLowerCase();

        if (guess.length() == 1) {
            boolean isCorrect = capitalName.contains(guess);

            if (isCorrect) {
                this.validLetters.add(guess);
            }
            return isCorrect;

        } else if (guess.length() > 1) {
            return capitalName.equals(guess);
        }

        throw new IllegalStateException("Invalid state, guess shouldn't be empty.");
    }

    public void collectSentenceToProperContainer(String sentence) {
        sentence = sentence.toLowerCase();

        if (sentence.length() == 1) {
            this.usedLetters.add(sentence);

        } else if (sentence.length() > 1) {
            this.usedWords.add(sentence);
        }
    }

    public boolean isAlreadyUsed(String sentence) {
        sentence = sentence.toLowerCase();
        return this.usedWords.contains(sentence) || this.usedLetters.contains(sentence);
    }

    public Country getCountry( ) {
        return country;
    }

    public Set<String> getValidLetters( ) {
        return validLetters;
    }

    public Set<String> getUsedLetters( ) {
        return usedLetters;
    }

    public Set<String> getUsedWords( ) {
        return usedWords;
    }
}
