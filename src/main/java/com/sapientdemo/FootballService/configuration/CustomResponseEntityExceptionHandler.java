package com.sapientdemo.FootballService.configuration;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sapientdemo.FootballService.service.impl.FootballServiceImpl;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(CustomResponseEntityExceptionHandler.class);

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.info(MessageFormat.format("Method Argument not valid; Status: ", null));
		return new ResponseEntity(null, headers, status);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.info(MessageFormat.format("Media Type Not Supported; Status: ", null));
		return new ResponseEntity(null, headers, status);
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleExceptions(Exception ex) {

		logger.info(MessageFormat.format("Excpetion Occured: {0} ", ex.getMessage()));
		return buildResponseEntity(null, HttpStatus.BAD_REQUEST);
	}

	private ResponseEntity<Object> buildResponseEntity(String error, HttpStatus status) {
		return new ResponseEntity<>(error, status);
	}
}
