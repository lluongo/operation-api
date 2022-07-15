package com.tenpo.operationapi.controllers;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tenpo.operationapi.exceptions.BadRequestException;
import com.tenpo.operationapi.models.RequestHistory;
import com.tenpo.operationapi.payload.request.LoginRequest;
import com.tenpo.operationapi.payload.request.SignupRequest;
import com.tenpo.operationapi.payload.response.JwtResponse;
import com.tenpo.operationapi.payload.response.SignUpResponse;
import com.tenpo.operationapi.services.AuthService;
import com.tenpo.operationapi.services.HistoryLogService;
import com.tenpo.operationapi.services.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/auth")
public class AuthController {
	@Autowired
	AuthService authService;

	@Autowired
	UserService userService;

	@Autowired
	HistoryLogService historyLogService;

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest)
			throws JsonProcessingException {

		RequestHistory requestHistory = historyLogService
				.save(new RequestHistory("/v1/auth/signup", "POST", signUpRequest.serializeMe(), new Date()));

		userService.getByUserName(signUpRequest.getUsername()).ifPresent(s -> {
			throw new BadRequestException(null, null, "Is already taken",
					"The Username is already taken!, please select another");
		});

		userService.getByEmail(signUpRequest.getEmail()).ifPresent(s -> {
			throw new BadRequestException(null, null, "Already in use",
					"The Email is already in use!, please select another");
		});

		SignUpResponse signUpResponse = authService.signUp(signUpRequest);

		requestHistory.setJsonResponse(signUpResponse.serializeMe());
		historyLogService.save(requestHistory);

		return ResponseEntity.ok(signUpResponse);
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest)
			throws JsonProcessingException {

		RequestHistory requestHistory = historyLogService
				.save(new RequestHistory("/v1/auth/signin", "POST", loginRequest.serializeMe(), new Date()));

		JwtResponse jwtResponse = authService.login(loginRequest);

		requestHistory.setJsonResponse(jwtResponse.serializeMe());
		historyLogService.save(requestHistory);

		return ResponseEntity.ok(jwtResponse);
	}
}
