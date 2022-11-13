package com.example.sharingcar.user.service.impl;

import com.example.sharingcar.component.MailComponent;
import com.example.sharingcar.user.entity.User;
import com.example.sharingcar.user.exception.EmailNotAuthException;
import com.example.sharingcar.user.model.UserInput;
import com.example.sharingcar.user.repository.UserRepository;
import com.example.sharingcar.user.service.UserService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final MailComponent mailComponent;

	@Override
	public boolean register(UserInput parameter) {

		Optional<User> optionalUser = userRepository.findById(parameter.getEmail());
		if (optionalUser.isPresent()) {
			return false;
		}
		String encPassword = BCrypt.hashpw(parameter.getPassword(), BCrypt.gensalt());
		String uuid = UUID.randomUUID().toString();
		User user = User.builder()
			.email(parameter.getEmail())
			.password(encPassword)
			.name(parameter.getName())
			.birth(parameter.getBirth())
			.phone(parameter.getPhone())
			.license(parameter.getLicense())
			.address(parameter.getAddress())
			.detail(parameter.getDetail())
			.regDt(LocalDateTime.now())
			.emailAuthYn(false)
			.emailAuthKey(uuid).build();
		userRepository.save(user);

		String email = parameter.getEmail();
		String subject = "회원가입을 축하드립니다. 계정을 활성화하세요.";
		String text = " <p>자차보단 공유차, SHARINGCAR입니다.</p> "
			+ " <p>" + parameter.getName() + "님! 회원가입을 축하드립니다.</p> "
			+ " <p>링크를 클릭해 계정을 활성화하세요!</p> "
			+ " <a href=http://localhost:8080/user/authorize_email?id=" + uuid + ">계정 활성화</a>";
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
		if (user.isEmailAuthYn()) {
			return false;
		}
		user.setEmailAuthYn(true);
		user.setEmailAuthDt(LocalDateTime.now());
		userRepository.save(user);

		return true;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optionalUser = userRepository.findById(username);
		if (!optionalUser.isPresent()) {
			throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
		}

		User user = optionalUser.get();
		if (!user.isEmailAuthYn()) {
			throw new EmailNotAuthException("이메일을 인증해 주세요.");
		}
		List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
		grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_USER"));

		if (user.isAdminYn()) {
			grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}

		return new org.springframework.security.core.userdetails.User(
			user.getEmail(), user.getPassword(), grantedAuthorityList
		);
	}
}
