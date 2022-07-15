package com.tenpo.operationapi.services;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tenpo.operationapi.event.OnUserLogoutSuccessEvent;
import com.tenpo.operationapi.exceptions.BadRequestException;
import com.tenpo.operationapi.models.ERole;
import com.tenpo.operationapi.models.Role;
import com.tenpo.operationapi.models.Token;
import com.tenpo.operationapi.models.User;
import com.tenpo.operationapi.payload.request.LogOutRequest;
import com.tenpo.operationapi.payload.request.LoginRequest;
import com.tenpo.operationapi.payload.request.SignupRequest;
import com.tenpo.operationapi.payload.response.JwtResponse;
import com.tenpo.operationapi.payload.response.LogOutResponse;
import com.tenpo.operationapi.payload.response.SignUpResponse;
import com.tenpo.operationapi.repository.RoleRepository;
import com.tenpo.operationapi.security.services.UserDetailsImpl;

@Service
public class AuthService {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserService userService;

	@Autowired
	TokenService tokenService;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	ApplicationEventPublisher applicationEventPublisher;

	public SignUpResponse signUp(SignupRequest signUpRequest) {

		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()), new Date());

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userService.save(user);

		return new SignUpResponse(user.getId(), user.getUsername(), user.getEmail(), user.getRoles(), new Date());
	}

	public JwtResponse login(LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		Token jwt = tokenService.getValidToken(userDetails);

		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return new JwtResponse(jwt.getTokenValue(), roles, jwt.getCreationDate());

	}

	public LogOutResponse logout(UserDetailsImpl currentUser, LogOutRequest logOutRequest) {

		if (!tokenService.getUserNameFromJwtToken(logOutRequest.getToken()).equals(currentUser.getUsername())) {
			throw new BadRequestException(null, null, "Prohibited Action", "Can't log out another user");
		}

		List<Token> validsJwt = tokenService.getValidTokensByUserId(currentUser.getId());

		validsJwt.stream().forEach(jwt -> {
			addTokenInCache(jwt.getTokenValue(), currentUser.getEmail(), logOutRequest);
		});

		tokenService.deleteAllByUserId(currentUser.getId());

		tokenService.getTokensByTokenValue(logOutRequest.getToken()).ifPresentOrElse(null,
				() -> addTokenInCache(logOutRequest.getToken(), currentUser.getEmail(), logOutRequest));

		return new LogOutResponse("User has successfully logged out from the system!");

	}

	private void addTokenInCache(String jwt, String email, LogOutRequest logOutRequest) {

		OnUserLogoutSuccessEvent logoutSuccessEvent = new OnUserLogoutSuccessEvent(email, jwt, logOutRequest);
		applicationEventPublisher.publishEvent(logoutSuccessEvent);

	}

}
