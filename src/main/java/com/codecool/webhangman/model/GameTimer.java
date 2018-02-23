package com.codecool.webhangman.model;

public class GameTimer {
    private long seconds;
    private long minutes;

    public GameTimer(long millis) {
        long seconds = millis / 1000;

        this.seconds = seconds;
        this.minutes = (seconds / 60);
    }

    public long getSeconds( ) {
        return seconds;
    }

    public long getMinutes( ) {
        return minutes;
    }

    public String convertToTwoDigitFormat(long number) {
        String stringRepresentation = String.valueOf(number);
        return stringRepresentation.length() < 2 ? "0" + stringRepresentation : stringRepresentation;
    }
}
