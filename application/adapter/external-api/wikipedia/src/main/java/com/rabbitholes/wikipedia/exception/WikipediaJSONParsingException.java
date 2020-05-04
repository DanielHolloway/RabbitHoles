package com.rabbitholes.wikipedia.exception;

public class WikipediaJSONParsingException extends RuntimeException {
    public WikipediaJSONParsingException(final String message) {
        super(message);
    }
}
