
package com.tenpo.operationapi.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tenpo.operationapi.payload.request.OperationRequest;
import com.tenpo.operationapi.payload.response.OperationResponse;
import com.tenpo.operationapi.services.OperationService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/operation")
public class OperationController {

	@Autowired
	OperationService operationService;

	@PostMapping("")
	public ResponseEntity<?> operation(@Valid @RequestBody OperationRequest operationRequest) {
		return ResponseEntity.ok(new OperationResponse(
				operationService.add(operationRequest.getOperator1(), operationRequest.getOperator2())));
	}
}