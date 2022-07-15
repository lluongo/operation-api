package com.tenpo.operationapi.controllers;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tenpo.operationapi.models.RequestHistory;
import com.tenpo.operationapi.payload.request.LogOutRequest;
import com.tenpo.operationapi.payload.response.LogOutResponse;
import com.tenpo.operationapi.security.services.CurrentUser;
import com.tenpo.operationapi.security.services.UserDetailsImpl;
import com.tenpo.operationapi.services.AuthService;
import com.tenpo.operationapi.services.HistoryLogService;
import com.tenpo.operationapi.services.TokenService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/user")
public class UserController {

	@Autowired
	TokenService tokenService;

	@Autowired
	AuthService authService;

	@Autowired
	HistoryLogService historyLogService;

	@PutMapping("/signout")
	public ResponseEntity<?> deauthenticateUser(@CurrentUser UserDetailsImpl currentUser,
			@Valid @RequestBody LogOutRequest logOutRequest) throws JsonProcessingException {

		RequestHistory requestHistory = historyLogService
				.save(new RequestHistory("/v1/user/signout", "PUT", logOutRequest.serializeMe(), new Date()));

		LogOutResponse response = authService.logout(currentUser, logOutRequest);
		
		requestHistory.setJsonResponse(response.serializeMe());
		historyLogService.save(requestHistory);

		return ResponseEntity.ok(response);

	}
}