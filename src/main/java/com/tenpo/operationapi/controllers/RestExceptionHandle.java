package com.tenpo.operationapi.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.tenpo.operationapi.dtos.ApiError;
import com.tenpo.operationapi.exceptions.BadRequestException;

@ControllerAdvice
public class RestExceptionHandle extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ApiError apiError = new ApiError();
		apiError.setType("Bad Request");
		apiError.setTitle("Validation errors in input fields");

		for (ObjectError err : ex.getAllErrors()) {
			apiError.getDetail().put(err.getCodes()[1].split("\\.")[1], err.getDefaultMessage());
		}

		return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(BadRequestException.class)
	protected ResponseEntity<Object> handleBadRequestException(HttpServletRequest request,BadRequestException ex) {

		ApiError apiError = new ApiError();
		apiError.setType(ex.getType());
		apiError.setTitle(ex.getCustomMessage());

		return buildResponseEntity(apiError,HttpStatus.BAD_REQUEST);
	}

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError,HttpStatus status){
        return new ResponseEntity<Object>(apiError,status);
    }

}