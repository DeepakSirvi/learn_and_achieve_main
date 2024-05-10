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
public class ExceptionResponse {

	private Boolean success;
	private String message;
	private HttpStatus status;

	public ExceptionResponse(String message) {
		this.message = message;
		this.success = Boolean.FALSE;
		this.status = HttpStatus.BAD_REQUEST;
	}
}
