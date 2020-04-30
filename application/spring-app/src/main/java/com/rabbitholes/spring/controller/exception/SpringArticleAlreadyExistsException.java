package com.rabbitholes.spring.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Article already exists")
public class SpringArticleAlreadyExistsException extends RuntimeException {
}