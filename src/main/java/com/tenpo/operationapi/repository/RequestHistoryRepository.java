package com.tenpo.operationapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tenpo.operationapi.models.RequestHistory;

@Repository
public interface RequestHistoryRepository extends JpaRepository<RequestHistory, Long> {

	Page<RequestHistory> findAll(Pageable pageable);

}
