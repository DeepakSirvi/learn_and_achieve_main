package com.dollop.app.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DuplicateEntryException extends RuntimeException{

	private ExceptionResponse exceptionResponse;

	public DuplicateEntryException(String message) {
		exceptionResponse = new ExceptionResponse();
		exceptionResponse.setSuccess(Boolean.FALSE);
		exceptionResponse.setMessage(message);
		exceptionResponse.setStatus(HttpStatus.NOT_ACCEPTABLE);
	}
}
