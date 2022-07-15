package com.tenpo.operationapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tenpo.operationapi.payload.response.HistoryLogResponse;
import com.tenpo.operationapi.services.HistoryLogService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/report")
public class ReportController {

	@Autowired
	HistoryLogService historyLogService;

	@GetMapping("/historyLog")
	public ResponseEntity<?> get(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {

		HistoryLogResponse historyLogResponse = historyLogService
				.getAllByPageSizeAndPageNumber(PageRequest.of(pageNumber == null ? 0 : pageNumber,
						pageSize == null ? Integer.MAX_VALUE : pageSize, Direction.DESC, "creationDate"));

		return ResponseEntity.ok(historyLogResponse);

	}
}