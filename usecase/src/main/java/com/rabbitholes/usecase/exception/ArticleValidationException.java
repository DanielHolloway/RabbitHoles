package com.rabbitholes.usecase.exception;

public class ArticleValidationException extends RuntimeException {
	public ArticleValidationException(final String message) {
		super(message);
	}
}
