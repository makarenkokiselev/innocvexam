package com.pablo.innocvexam.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.pablo.innocvexam.exception.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(RuntimeException.class)
	protected ResponseEntity<ErrorMessage> handleRuntimeException(RuntimeException ex, WebRequest request) {
		if (ex instanceof IllegalArgumentException) {
			IllegalArgumentException illargex = (IllegalArgumentException) ex;
			return ResponseEntity
					.badRequest()
					.body(ErrorMessage.builder().message(illargex.getMessage()).build());
		} else if (ex instanceof EntityNotFoundException) {
			EntityNotFoundException enfex = (EntityNotFoundException) ex;
			return ResponseEntity
					.status(HttpStatus.NO_CONTENT)
					.body(ErrorMessage.builder().message(enfex.getMessage()).build());
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
}