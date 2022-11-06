package com.example.sharingcar.user.model;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class UserInput {

	private String email;
	private String password;
	private String name;
	private String birth;
	private String phone;
	private String license;
	private String address;
	private String detail;

}
