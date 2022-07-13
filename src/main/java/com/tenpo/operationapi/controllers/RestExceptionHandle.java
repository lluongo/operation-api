package com.tenpo.operationapi.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.tenpo.operationapi.dtos.ApiError;

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

//	@ExceptionHandler(ResourceNotFoundException.class)
//	protected ResponseEntity<Object> handleResourceNotFoundException(HttpServletRequest request,ResourceNotFoundException ex) {
//
//		System.out.println("asdfasdfasdfasdfasdfg");
//		
//		ApiError apiError = new ApiError();
//		apiError.setType("Bad Request");
//		apiError.setTitle(ex.getMessage());
//
//		return buildResponseEntity(apiError,HttpStatus.BAD_REQUEST);
//	}
//
//	private ResponseEntity<Object> buildResponseEntity(ApiError apiError,HttpStatus status){
//        return new ResponseEntity<Object>(apiError,status);
//    }

}