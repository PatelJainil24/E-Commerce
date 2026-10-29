package com.app.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ErrorApi> handleException(ProductNotFoundException ex) {
		log.error("User Not Found Exception {}",ex.getMessage());
		
		ErrorApi errorAPI = new ErrorApi();
		errorAPI.setMessage(ex.getMessage());
		errorAPI.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
		errorAPI.setError("Validatoin error");
		
		return new ResponseEntity<ErrorApi>(errorAPI,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorApi> handleException(Exception ex) {
		log.error("Internal Server Error {}",ex.getMessage());
		
		ErrorApi errorAPI = new ErrorApi();
		errorAPI.setMessage(ex.getMessage());
		//errorAPI.setError("Something went wrong");
		errorAPI.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		
		return new ResponseEntity<>(errorAPI,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorApi> handleException(MethodArgumentNotValidException ex) {
		
		String errors = ex.getBindingResult()
		  .getFieldErrors()
		  .stream()
		  .map(obj -> obj.getField()+":"+obj.getDefaultMessage())
		  .collect(Collectors.joining(","));
		
		
		ErrorApi errorAPI = new ErrorApi	();
		
		errorAPI.setMessage(errors);
		errorAPI.setError("Client Side Validation Error");
		errorAPI.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
		
		return new ResponseEntity<>(errorAPI,HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorApi> handleException(HttpMessageNotReadableException ex) {
		log.error("HttpMessageNotReadableException Exception {}",ex.getMessage());
		
		ErrorApi errorAPI = new ErrorApi();
		
		errorAPI.setMessage(ex.getMessage());
		errorAPI.setError("Malformed JSON Data");
		errorAPI.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
		
		return new ResponseEntity<>(errorAPI,HttpStatus.BAD_REQUEST);
		
	}
}
