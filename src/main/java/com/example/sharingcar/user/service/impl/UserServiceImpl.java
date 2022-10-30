package com.example.sharingcar.user.service.impl;

import com.example.sharingcar.component.MailComponent;
import com.example.sharingcar.user.entity.User;
import com.example.sharingcar.user.model.UserInput;
import com.example.sharingcar.user.repository.UserRepository;
import com.example.sharingcar.user.service.UserService;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final MailComponent mailComponent;

	@Override
	public boolean register(UserInput parameter) {

		Optional<User> optionalUser = userRepository.findById("email");
		if (optionalUser.isPresent()) {
			return false;
		}
		String uuid = UUID.randomUUID().toString();

		User user = new User();
		user.setEmail(parameter.getEmail());
		user.setPassword(parameter.getPassword());
		user.setName(parameter.getName());
		user.setBirth(parameter.getBirth());
		user.setPhone(parameter.getPhone());
		user.setLicense(parameter.getLicense());
		user.setAddress(parameter.getAddress());
		user.setDetail(parameter.getDetail());
		user.setRegDt(LocalDateTime.now());
		user.setEmailAuthYn(false);
		user.setEmailAuthKey(uuid);
		userRepository.save(user);

		String email = parameter.getEmail();
		String subject = "회원가입을 축하드립니다. 계정을 활성화하세요.";
		String text = "<html><body>"
			+ "<p>자차보단 공유차, SHARINGCAR입니다.</p>"
			+ "<p>" + parameter.getName() + "님! 회원가입을 축하드립니다.</p>"
			+ "<p>링크를 클릭해 계정을 활성화하세요!</p>"
			+ "<a href='http://localhost:8080/user/authorize_email?id=" + uuid + ">계정 활성화</a>"
			+ "</body></html>";
		mailComponent.sendMail(email, subject, text);

		return true;
	}

	@Override
	public boolean authorizeEmail(String uuid) {
		Optional<User> optionalUser = userRepository.findByEmailAuthKey(uuid);
		if (!optionalUser.isPresent()) {
			return false;
		}

		User user = optionalUser.get();
		user.setEmailAuthYn(true);
		user.setEmailAuthDt(LocalDateTime.now());
		userRepository.save(user);

		return true;
	}
}
