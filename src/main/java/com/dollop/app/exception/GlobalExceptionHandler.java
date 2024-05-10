package com.dollop.app.exception;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;



@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ExceptionResponse> resolveException(BadRequestException exception) {
		ExceptionResponse exceptionResponse = exception.getExceptionResponse();
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DuplicateEntryException.class)
	public ResponseEntity<ExceptionResponse> resolveException(DuplicateEntryException exception) {
		ExceptionResponse exceptionResponse = exception.getExceptionResponse();
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<ExceptionResponse> resolveException(SQLIntegrityConstraintViolationException exception) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage());
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ExceptionResponse> resolveException(DataIntegrityViolationException exception) {
		ExceptionResponse exceptionResponse = new ExceptionResponse("Can Not Delete Due to its child");
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class) 
	public ResponseEntity<Map<String,String>> handlerMethodArgsNotVAlidException(MethodArgumentNotValidException ex)
	{
		Map<String,String> resp = new HashMap<>();
		List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();   //x= Return a list of error 
	    allErrors.forEach((error)->{
	    	String field = ((FieldError)error).getField();
	    	String defaultMessage = error.getDefaultMessage();
	    	resp.put(field,defaultMessage);
	    });
		return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NumberFormatException.class)
	public ResponseEntity<ExceptionResponse> resolveException(NumberFormatException exception) {
		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setMessage(exception.getMessage());
		exceptionResponse.setStatus(HttpStatus.BAD_REQUEST);
		exceptionResponse.setSuccess(Boolean.FALSE);
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ExceptionResponse> resolveException(ResourceNotFoundException exception) {
		ExceptionResponse exceptionResponse = exception.getExceptionResponse();
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ExceptionResponse> resolveException(UnauthorizedException exception) {
		ExceptionResponse exceptionResponse = exception.getExceptionResponse();
		return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
	}
	
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ExceptionResponse> resolveException(MethodArgumentTypeMismatchException exception) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage());
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
}