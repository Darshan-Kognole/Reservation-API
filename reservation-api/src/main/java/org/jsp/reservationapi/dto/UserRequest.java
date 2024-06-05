package org.jsp.reservationapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {
	@NotBlank(message =  "name is manadatory")
	private String name;
	private int age;
	private long phone;
	@NotBlank(message = "Email is Manadatory")
	@Email(message = "Invalid Email Format")
	private String email;
	@NotBlank(message = "Gender is manadatory")
	private String gender;
	@NotBlank(message ="Password is mandatory")
	@Size(min=8,max=15,message="Password length must be between 8-15 character")
	private String password;
}
