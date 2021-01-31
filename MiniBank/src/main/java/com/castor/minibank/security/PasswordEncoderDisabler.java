package com.castor.minibank.security;

import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderDisabler implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		
		return rawPassword.toString();
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		
		return rawPassword.toString().equals(encodedPassword);
	}
}
