package com.castor.minibank.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDto {
	
	String email;
	
	String firstName;
	
	String middleName;
	
	String lastName;
	
	String password;
	
	public UserDto() {
		super();
	}
	
	public UserDto(String email, String firstName, String middleName, String lastName, String password) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.password = password;
	}

}
