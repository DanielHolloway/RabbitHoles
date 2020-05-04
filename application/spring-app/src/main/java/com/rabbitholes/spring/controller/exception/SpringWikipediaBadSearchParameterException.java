package com.rabbitholes.spring.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Bad search parameter")
public class SpringWikipediaBadSearchParameterException extends RuntimeException {
}
