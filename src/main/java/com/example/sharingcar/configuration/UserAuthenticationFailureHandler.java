package com.example.sharingcar.configuration;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class UserAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) throws IOException, ServletException {

		String msg = "로그인에 실패했습니다.";
		if (exception instanceof BadCredentialsException) {
			msg = "아이디 및 비밀번호가 일치하지 않습니다.";
		}
		if (exception instanceof InternalAuthenticationServiceException) {
			msg = "이메일 인증 후 이용해주세요.";
		}

		setUseForward(true);
		setDefaultFailureUrl("/user/login?error=true");
		request.setAttribute("errorMessage", msg);

		super.onAuthenticationFailure(request, response, exception);
	}
}
