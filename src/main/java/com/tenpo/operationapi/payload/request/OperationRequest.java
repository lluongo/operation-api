package com.tenpo.operationapi.payload.request;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.operationapi.payload.ISerializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OperationRequest implements ISerializable {

	@Valid
	@NotNull(message = "Operator1 cannot be null")
	private BigDecimal operator1;

	@Valid
	@NotNull(message = "Operator2 cannot be null")
	private BigDecimal operator2;

	@Override
	public String serializeMe() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(this);
	}
}
