package com.codecool.webhangman.model;

public class GameTimer {
    private long seconds;
    private long minutes;
    private long millis;

    public GameTimer(long millis) {
        this.millis = millis;

        long seconds = millis / 1000;
        this.seconds = (seconds % 60);
        this.minutes = (seconds / 60);
    }

    public long getSeconds( ) {
        return seconds;
    }

    public long getMinutes( ) {
        return minutes;
    }

    public long getMillis() {
        return millis;
    }

    public String convertToTwoDigitFormat(long number) {
        String stringRepresentation = String.valueOf(number);
        return stringRepresentation.length() < 2 ? "0" + stringRepresentation : stringRepresentation;
    }
}
