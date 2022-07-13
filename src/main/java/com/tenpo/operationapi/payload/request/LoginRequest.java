package com.tenpo.operationapi.payload.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;

@Getter
public class LoginRequest {
	@NotBlank
	private String username;

	@NotBlank
	private String password;

}
