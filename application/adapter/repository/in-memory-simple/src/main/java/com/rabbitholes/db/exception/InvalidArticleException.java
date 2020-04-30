package com.rabbitholes.db.exception;

public class InvalidArticleException extends RuntimeException {
    public InvalidArticleException(final String message) {
        super(message);
    }
}