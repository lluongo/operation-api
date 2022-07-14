package com.tenpo.operationapi.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tenpo.operationapi.payload.request.LogOutRequest;
import com.tenpo.operationapi.security.services.CurrentUser;
import com.tenpo.operationapi.security.services.UserDetailsImpl;
import com.tenpo.operationapi.services.AuthService;
import com.tenpo.operationapi.services.TokenService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/user")
public class UserController {

	@Autowired
	TokenService tokenService;

	@Autowired
	AuthService authService;

	@PutMapping("/signout")
	public ResponseEntity<?> deauthenticateUser(@CurrentUser UserDetailsImpl currentUser,
			@Valid @RequestBody LogOutRequest logOutRequest) {

		authService.logout(currentUser, logOutRequest);

		return ResponseEntity.ok("User has successfully logged out from the system!");

	}
}