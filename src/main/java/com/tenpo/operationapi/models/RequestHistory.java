package com.tenpo.operationapi.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "RequestHistory")
@NoArgsConstructor
@Getter
@Setter
public class RequestHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String endpoint;

	private String method;

	private String jsonRequest;

	private String jsonResponse;

	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	public RequestHistory(String endpoint, String method, String jsonRequest, Date date) {
		this.endpoint = endpoint;
		this.method = method;
		this.jsonRequest = jsonRequest;
		this.creationDate = date;
	}
}