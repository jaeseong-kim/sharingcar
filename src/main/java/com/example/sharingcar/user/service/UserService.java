package com.example.sharingcar.user.service;

import com.example.sharingcar.user.model.UserInput;

public interface UserService {

	boolean register(UserInput userInput);

	boolean authorizeEmail(String uuid);
}
