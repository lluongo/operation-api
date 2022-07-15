package com.tenpo.operationapi.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

@Service
public class OperationService {

	public BigDecimal add(BigDecimal operator1, BigDecimal operator2) {
		return operator1.add(operator2);
	}

}