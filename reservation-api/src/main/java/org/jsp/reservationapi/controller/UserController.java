package org.jsp.reservationapi.controller;

import org.jsp.reservationapi.dto.ResponseStructure;
import org.jsp.reservationapi.dto.UserRequest;
import org.jsp.reservationapi.dto.UserResponse;
import org.jsp.reservationapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<UserResponse>> saveUser(@RequestBody UserRequest userRequest, HttpServletRequest request){
		return userService.saveUser(userRequest,request);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<UserResponse>> findById(@PathVariable int id){
		return userService.findById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ResponseStructure<UserResponse>> updateUser(@RequestBody UserRequest userRequest,@PathVariable int id){
		return userService.updateUser(userRequest,id);
	}
	
	@PostMapping("/verify-by-phone")
	public ResponseEntity<ResponseStructure<UserResponse>> verifyUser(@RequestParam long phone, @RequestParam String password){
		return userService.verifyUser(phone,password);
	}
	
	@PostMapping("/verify-by-email")
	public ResponseEntity<ResponseStructure<UserResponse>> verifyUser(@RequestParam String email, @RequestParam String password){
		return userService.verifyUser(email,password);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteUser(@PathVariable(name="id") int id){
		return userService.deleteUser(id);
	}
	
	@GetMapping("/activate")
	public String activate(@RequestParam String token) {
		return userService.activate(token);
	}
}
