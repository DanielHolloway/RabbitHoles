package com.rabbitholes.spring.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Wikipedia API promise failed")
public class SpringWikipediaFutureException extends RuntimeException {
}
