package com.example.sharingcar.user.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class User {

	@Id
	private String email;

	private String password;
	private String name;
	private String birth;
	private String phone;
	private String license;
	private String address;
	private String detail;
	private LocalDateTime regDt;
	private boolean emailAuthYn;
	private String emailAuthKey;
	private LocalDateTime emailAuthDt;


}
