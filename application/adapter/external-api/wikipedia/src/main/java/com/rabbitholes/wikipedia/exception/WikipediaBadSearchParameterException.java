package com.rabbitholes.wikipedia.exception;

public class WikipediaBadSearchParameterException extends RuntimeException {
    public WikipediaBadSearchParameterException(String message){
        super(message);
    }
}
