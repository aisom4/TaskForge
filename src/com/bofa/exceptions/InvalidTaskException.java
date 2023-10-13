package com.bofa.exceptions;

public class InvalidTaskException extends Exception {

    private final String description;

    public InvalidTaskException(String message, String description) {
        super(message);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
