package com.example.sharingcar.user.controller;

import com.example.sharingcar.car.service.CarService;
import com.example.sharingcar.reserve.entity.Reserve;
import com.example.sharingcar.reserve.service.ReserveService;
import com.example.sharingcar.user.entity.User;
import com.example.sharingcar.user.model.UserInput;
import com.example.sharingcar.user.service.UserService;
import java.security.Principal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
public class UserController {

	private final UserService userService;
	private final CarService carService;
	private final ReserveService reserveService;

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

	@RequestMapping("/user/login")
	public String login() {

		return "user/login";
	}

	@GetMapping("/user/info")
	public String info(Model model, Principal principal) {

		User user = userService.getUser(principal.getName());
		List<Reserve> list = reserveService.listForMyPage(user);

		model.addAttribute("list", list);

		return "user/info";
	}


}
