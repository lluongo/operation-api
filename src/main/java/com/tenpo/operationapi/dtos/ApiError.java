package com.tenpo.operationapi.dtos;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiError {

	private String type;
	private String title;
	private Map<String, String> detail = new HashMap<>();
}
