package org.jsp.reservationapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AdminRequest {
	private long phone;
	@Email(message = "Invalid Format")
	private String email;
	@NotBlank(message = "GST number is manadatorily 15 character")
	@Size(min=15,max=15)
	private String gst_number;
	@NotBlank(message = "Name is mandatory")
	private String name;
	@NotBlank(message = "Travels Name is manadatory")
	private String travels_name;
	@NotBlank(message ="Password is mandatory")
	@Size(min=8,max=15,message="Password length must be between 8-15 character")
	private String password;
}
