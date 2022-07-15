package com.tenpo.operationapi.payload.request;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OperationRequest {

	@Valid
	@NotNull(message = "Operator1 cannot be null")
	private BigDecimal operator1;

	@Valid
	@NotNull(message = "Operator2 cannot be null")
	private BigDecimal operator2;
}
