package com.tenpo.operationapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tenpo.operationapi.models.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
	List<Token> getByUserId(Long userId);

	Optional<Token> getTokensByTokenValue(String jwt);

}
