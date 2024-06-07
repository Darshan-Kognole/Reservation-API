package org.jsp.reservationapi.service;

import java.util.Optional;

import org.jsp.reservationapi.dao.UserDao;
import org.jsp.reservationapi.dto.EmailConfiguration;
import org.jsp.reservationapi.dto.ResponseStructure;
import org.jsp.reservationapi.dto.UserRequest;
import org.jsp.reservationapi.dto.UserResponse;
import org.jsp.reservationapi.exception.UserNotFoundException;
import org.jsp.reservationapi.model.User;
import org.jsp.reservationapi.util.AccountStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;

@Service
@Data
public class UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private LinkGeneratorService linkGeneratorService;
	@Autowired
	private EmailConfiguration emailConfiguration;
	@Autowired
	private ReservationApiMailService mailService;
	
	public ResponseEntity<ResponseStructure<UserResponse>> saveUser(UserRequest userRequest,HttpServletRequest request){
		ResponseStructure<UserResponse> structure = new ResponseStructure<>();
		User user=mapToUser(userRequest);
		user.setStatus(AccountStatus.IN_ACTIVE.toString());
		userDao.saveUser(user);
		String activation_link=linkGeneratorService.getUserActivationLink(user, request);
		emailConfiguration.setSubject("Activate Your Account");
		emailConfiguration.setText("Dear Admin please activate your account by clicking on the following link:\n "+ activation_link);
		emailConfiguration.setToAddress(user.getEmail());
		
		structure.setMessage(mailService.sendMail(emailConfiguration));
		structure.setData(mapToUserResponse(user));
		structure.setStatusCode(HttpStatus.CREATED.value());
		return ResponseEntity.status(HttpStatus.CREATED).body(structure);
	}
	
	public ResponseEntity<ResponseStructure<UserResponse>> findById(int id){
		Optional<User> recUser=userDao.findById(id);
		if(recUser.isPresent()) {
			ResponseStructure<UserResponse> structure = new ResponseStructure<>();
			structure.setMessage("User Found");
			structure.setData(mapToUserResponse(recUser.get()));
			structure.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new UserNotFoundException("User id is invalid");
	}
	
	public ResponseEntity<ResponseStructure<UserResponse>> updateUser(UserRequest userRequest,int id){
		Optional<User> recUser=userDao.findById(id);
		if(recUser.isPresent()) {
			User dbUser=recUser.get();
			dbUser.setAge(userRequest.getAge());
			dbUser.setEmail(userRequest.getEmail());
			dbUser.setGender(userRequest.getGender());
			dbUser.setName(userRequest.getName());
			dbUser.setPassword(userRequest.getPassword());
			dbUser.setPhone(userRequest.getPhone());
			ResponseStructure<UserResponse> structure = new ResponseStructure<>();
			structure.setMessage("User Updated");
			structure.setData(mapToUserResponse(userDao.saveUser(dbUser)));
			structure.setStatusCode(HttpStatus.ACCEPTED.value());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(structure);
		}
		throw new UserNotFoundException("User id is invalid");
	}
	
	public ResponseEntity<ResponseStructure<UserResponse>> verifyUser(long phone,String password){
		Optional<User> recUser =userDao.verifyUser(phone, password);
		if(recUser.isPresent()) {
			User dbuser = recUser.get();
			ResponseStructure<UserResponse> structure = new ResponseStructure<>();
			if(dbuser.getStatus().equals(AccountStatus.IN_ACTIVE.toString()))
				throw new IllegalStateException();
			
			structure.setMessage("User Verified Successfully");
			structure.setData(mapToUserResponse(recUser.get()));
			structure.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new UserNotFoundException("User phone or password is invalid");
	}
	
	public ResponseEntity<ResponseStructure<UserResponse>> verifyUser(String email,String password){
		Optional<User> recUser =userDao.verifyUser(email, password);
		if(recUser.isPresent()) {
			User dbuser = recUser.get();
			ResponseStructure<UserResponse> structure = new ResponseStructure<>();
			if(dbuser.getStatus().equals(AccountStatus.IN_ACTIVE.toString()))
				throw new IllegalStateException();
			
			structure.setMessage("user Verified Successfully");
			structure.setData(mapToUserResponse(recUser.get()));
			structure.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new UserNotFoundException("User Email or Password is invalid");
	}
	
	public ResponseEntity<ResponseStructure<String>> deleteUser(int id){
		Optional<User> recUser =userDao.findById(id);
		if(recUser.isPresent()) {
			ResponseStructure<String> structure = new ResponseStructure<>();
			structure.setMessage("User Deleted Successfully");
			structure.setData("User Deleted");
			structure.setStatusCode(HttpStatus.OK.value());
			userDao.deleteUser(id);
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new UserNotFoundException("User id is invalid");
	}
	
	private User mapToUser(UserRequest userRequest) {
		return User.builder().name(userRequest.getName()).age(userRequest.getAge()).phone(userRequest.getPhone())
				.email(userRequest.getEmail()).gender(userRequest.getGender()).password(userRequest.getPassword()).build();
	}
	
	private UserResponse mapToUserResponse(User user) {
		return UserResponse.builder().id(user.getId()) .name(user.getName()).age(user.getAge()).phone(user.getPhone())
				.email(user.getEmail()).gender(user.getGender()).password(user.getPassword()).build();
	}
	
	public String activate(String token) {
		Optional<User> recUser = userDao.findByToken(token);
		if (recUser.isEmpty())
			throw new UserNotFoundException("Invalid Token");
		User dbUser = recUser.get();
		dbUser.setStatus("ACTIVE");
		dbUser.setToken(null);
		userDao.saveUser(dbUser);
		return "Your Account has been activated";
	}
}
