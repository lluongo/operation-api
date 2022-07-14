package com.tenpo.operationapi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tenpo.operationapi.models.User;
import com.tenpo.operationapi.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	public User save(User user) {
		return userRepository.save(user);
	}

	public User getById(Long id) {
		return userRepository.getById(id);
	}

	public Optional<User> getByUserName(String username) {
		return userRepository.getByUsername(username);
	}

	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	public Optional<User> getByEmail(String email) {
		return userRepository.getByEmail(email);
	}

	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

}
