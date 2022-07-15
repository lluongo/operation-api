package com.tenpo.operationapi.payload.response;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OperationResponse {

	private BigDecimal total;
	private Date calculatedDate = new Date();

	public OperationResponse(BigDecimal total) {
		this.total = total;
	}

}
