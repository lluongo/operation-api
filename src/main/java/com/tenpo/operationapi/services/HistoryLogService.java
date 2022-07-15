package com.tenpo.operationapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tenpo.operationapi.models.RequestHistory;
import com.tenpo.operationapi.payload.response.HistoryLogResponse;
import com.tenpo.operationapi.repository.RequestHistoryRepository;

@Service
public class HistoryLogService {

	@Autowired
	RequestHistoryRepository requestHistoryRepository;

	public RequestHistory save(RequestHistory requestHistory) {
		return requestHistoryRepository.save(requestHistory);
	}

	public HistoryLogResponse getAllByPageSizeAndPageNumber(Pageable pageable) {
		Page<RequestHistory> pages = requestHistoryRepository.findAll(pageable);
		HistoryLogResponse historyLogResponse = new HistoryLogResponse(pages.getContent(), pages.getTotalPages());
		return historyLogResponse;
	}

}