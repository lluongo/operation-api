package com.tenpo.operationapi.security.jwt;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tenpo.operationapi.cache.LoggedOutJwtTokenCache;
import com.tenpo.operationapi.event.OnUserLogoutSuccessEvent;
import com.tenpo.operationapi.security.services.UserDetailsServiceImpl;

public class AuthTokenFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

	@Autowired
	private LoggedOutJwtTokenCache loggedOutJwtTokenCache;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String jwt = parseJwt(request);

			System.out.println(validateTokenIsNotForALoggedUser(jwt));

			if (jwt != null && validateTokenIsNotForALoggedUser(jwt) && jwtUtils.validateJwtToken(jwt)) {
				String username = jwtUtils.getUserNameFromJwtToken(jwt);
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			logger.error("Cannot set user authentication: {}", e);
		}

		filterChain.doFilter(request, response);
	}

	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}

		return null;
	}

	private boolean validateTokenIsNotForALoggedUser(String authToken) {
		OnUserLogoutSuccessEvent previouslyLoggedOutEvent = loggedOutJwtTokenCache.getLogoutEventForToken(authToken);
		if (previouslyLoggedOutEvent != null) {
			String userEmail = previouslyLoggedOutEvent.getUserEmail();
			Date logoutEventDate = previouslyLoggedOutEvent.getEventTime();
			logger.info("Token corresponds to an already logged out user [%s] at [%s]. Please login again", userEmail,
					logoutEventDate);
			return false;

		}
		return true;
	}

}
