package com.example.sharingcar.user.service;

import com.example.sharingcar.user.model.UserInput;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

	boolean register(UserInput userInput);

	boolean authorizeEmail(String uuid);
}
