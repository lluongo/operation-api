package com.tenpo.operationapi.payload.response;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Long id;
	private String username;
	private String email;
	private List<String> roles;
	private Date creationDate;

	public JwtResponse(String accessToken, Long id, String username, String email, List<String> roles,
			Date creationDate) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
		this.creationDate = creationDate;
	}

}
