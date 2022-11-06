package com.example.sharingcar.user.controller;

import com.example.sharingcar.user.model.UserInput;
import com.example.sharingcar.user.service.UserService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserController {

	private final UserService userService;

	@GetMapping("/user/register")
	public String register() {

		return "user/register";
	}

	@PostMapping("user/register")
	public String submitRegister(HttpServletRequest request, UserInput parameter) {

		userService.register(parameter);

		return "user/register_complete";
	}

	@GetMapping("user/authorize_email")
	public String authorizeEmail(Model model, HttpServletRequest request) {
		String uuid = request.getParameter("id");

		boolean result = userService.authorizeEmail(uuid);
		model.addAttribute("result", result);

		return "user/authorize_email";
	}
}
