package com.tenpo.operationapi.payload.response;

import java.util.Date;
import java.util.List;

import com.tenpo.operationapi.models.RequestHistory;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class HistoryLogResponse {

	private List<RequestHistory> histories;
	private int totalPages;
	private Date creationDate = new Date();

	public HistoryLogResponse(List<RequestHistory> histories, int totalPages) {
		this.histories = histories;
		this.totalPages = totalPages;
	}

}
