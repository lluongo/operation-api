package com.tenpo.operationapi.payload.response;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.operationapi.payload.ISerializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OperationResponse implements ISerializable {

	private BigDecimal total;
	private Date calculatedDate = new Date();

	public OperationResponse(BigDecimal total) {
		this.total = total;
	}

	@Override
	public String serializeMe() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(this);
	}
}
