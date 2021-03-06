package com.rabbitholes.spring.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FAILED_DEPENDENCY, reason = "Wikipedia API promise failed")
public class SpringWikipediaBadApiException extends RuntimeException {
}
