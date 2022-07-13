package com.tenpo.operationapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ResponseStatus(HttpStatus.NOT_FOUND)
public class BadRequestException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fieldName;
	private String fieldValue;
	private String messages;

	public BadRequestException(String fieldName, String fieldValue, String messages) {
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
		this.messages = messages;
	}
}
