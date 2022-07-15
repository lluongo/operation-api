package com.tenpo.operationapi.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tenpo.operationapi.models.Token;
import com.tenpo.operationapi.repository.TokenRepository;
import com.tenpo.operationapi.security.jwt.JwtUtils;
import com.tenpo.operationapi.security.services.UserDetailsImpl;

@Service
public class TokenService {

	@Autowired
	TokenRepository tokenRepository;

	@Autowired
	UserService userService;;

	@Autowired
	JwtUtils jwtUtils;

	public Token getValidToken(UserDetailsImpl userDetailImpl) {

		List<Token> jwtsList = tokenRepository.getByUserId(userDetailImpl.getId());

		if (jwtsList.isEmpty()) {
			Token token = new Token(jwtUtils.generateJwtToken(userDetailImpl),
					userService.getById(userDetailImpl.getId()), new Date());
			tokenRepository.save(token);
			return token;
		}

		return filterValidJwtsInList(jwtsList).get(0);
	}

	private List<Token> filterValidJwtsInList(List<Token> jwts) {
		return jwts.stream().filter(j -> jwtUtils.validateJwtToken(j.getTokenValue()) == true)
				.collect(Collectors.toList());
	}

	public List<Token> getValidTokensByUserId(Long userId) {
		return filterValidJwtsInList(tokenRepository.getByUserId(userId));
	}

	public void deleteAllByUserId(Long userId) {
		List<Long> jwtsList = tokenRepository.getByUserId(userId).stream().map(t -> t.getId())
				.collect(Collectors.toList());
		tokenRepository.deleteAllById(jwtsList);
	}

	public Optional<Token> getTokensByTokenValue(String jwt) {
		return tokenRepository.getTokensByTokenValue(jwt);
	}

	public String getUserNameFromJwtToken(String jwt) {
		return jwtUtils.getUserNameFromJwtToken(jwt);
	}

}
