package com.tenpo.operationapi.payload.response;

import java.util.Date;
import java.util.Set;

import com.tenpo.operationapi.models.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SignUpResponse {

	private Long id;
	private String username;
	private String email;
	private Set<Role> roles;
	private Date creationDate;

	public SignUpResponse(Long id, String username, String email, Set<Role> roles, Date creationDate) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
		this.creationDate= creationDate;
	}

}
