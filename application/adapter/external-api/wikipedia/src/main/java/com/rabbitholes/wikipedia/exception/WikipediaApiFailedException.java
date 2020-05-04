package com.rabbitholes.wikipedia.exception;

public class WikipediaApiFailedException extends RuntimeException {
    public WikipediaApiFailedException(final String message) {
        super(message);
    }
}