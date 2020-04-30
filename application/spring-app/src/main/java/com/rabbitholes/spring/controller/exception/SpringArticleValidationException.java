package com.rabbitholes.spring.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Article is invalid")
public class SpringArticleValidationException extends RuntimeException {
}
