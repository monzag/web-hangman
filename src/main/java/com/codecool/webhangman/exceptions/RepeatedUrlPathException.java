package com.codecool.webhangman.exceptions;

public class RepeatedUrlPathException extends RuntimeException {
    public RepeatedUrlPathException() {
        super("repeated paths");
    }
}
