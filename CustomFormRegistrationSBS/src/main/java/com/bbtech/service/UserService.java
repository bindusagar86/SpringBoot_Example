package com.bbtech.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.bbtech.model.User;
import com.bbtech.web.dto.UserRegistrationDto;

public interface UserService extends UserDetailsService {

	User findByEmail(String email);
	User save(UserRegistrationDto regUser);
}
