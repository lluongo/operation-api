package com.tenpo.operationapi.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
@Table(name = "Token", uniqueConstraints = { @UniqueConstraint(columnNames = "tokenValue") })
public class Token {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String tokenValue;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "id")
	private User user;

}