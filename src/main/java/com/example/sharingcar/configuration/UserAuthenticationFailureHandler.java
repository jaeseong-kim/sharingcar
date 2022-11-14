package com.example.sharingcar.configuration;

import com.example.sharingcar.user.error.ErrorType;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class UserAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) throws IOException, ServletException {

		ErrorType error = ErrorType.MISMATCH_ID_PASSWORD;

		if (exception instanceof InternalAuthenticationServiceException) {
			error = ErrorType.NOT_AUTH_EMAIL;
		}

		setUseForward(true);
		setDefaultFailureUrl("/user/login?error=true");
		request.setAttribute("errorMessage", error.getMessage());

		super.onAuthenticationFailure(request, response, exception);
	}
}
