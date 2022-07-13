package com.tenpo.operationapi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MessageResponse {
	private String message;
	private String description;
}
