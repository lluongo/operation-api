package com.tenpo.operationapi.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tenpo.operationapi.exceptions.BadRequestException;
import com.tenpo.operationapi.payload.request.LoginRequest;
import com.tenpo.operationapi.payload.request.SignupRequest;
import com.tenpo.operationapi.services.AuthService;
import com.tenpo.operationapi.services.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/auth")
public class AuthController {
	@Autowired
	AuthService authService;

	@Autowired
	UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

		userService.getByUserName(signUpRequest.getUsername()).ifPresent(s -> {
			throw new BadRequestException(null, null, "Is already taken",
					"The Username is already taken!, please select another");
		});

		userService.getByUserName(signUpRequest.getUsername()).ifPresent(s -> {
			new BadRequestException(null, null, "Already in use",
					"The Email is already in use!, please select another");
		});

		authService.signUp(signUpRequest);

		return ResponseEntity.ok("User registered successfully!");
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		return ResponseEntity.ok(authService.login(loginRequest));
	}
}
