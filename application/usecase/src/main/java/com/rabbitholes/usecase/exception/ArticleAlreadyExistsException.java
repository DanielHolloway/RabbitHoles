package com.rabbitholes.usecase.exception;

public class ArticleAlreadyExistsException extends RuntimeException {
	public ArticleAlreadyExistsException(final String title) {
		super(title);
	}
}
