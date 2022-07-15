package com.tenpo.operationapi.payload.response;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.operationapi.payload.ISerializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class JwtResponse implements ISerializable {
	private String token;
	private String type = "Bearer";
	private List<String> roles;
	private Date creationDate;

	public JwtResponse(String accessToken, List<String> roles, Date creationDate) {
		this.token = accessToken;
		this.roles = roles;
		this.creationDate = creationDate;
	}

	@Override
	public String serializeMe() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(this);
	}
}
