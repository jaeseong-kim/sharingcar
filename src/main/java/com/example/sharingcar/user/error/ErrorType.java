package com.example.sharingcar.user.error;

public enum ErrorType {
	MISMATCH_ID_PASSWORD("아이디 및 비밀번호가 일치하지 않습니다."),
	NOT_AUTH_EMAIL("이메일 인증 후 이용해주세요");

	private String s;

	ErrorType(String s) {
		this.s = s;
	}

	public String getS() {
		return s;
	}
}
