package org.jsp.reservationapi.service;

import java.util.Optional;

import org.jsp.reservationapi.dao.AdminDao;
import org.jsp.reservationapi.dto.AdminRequest;
import org.jsp.reservationapi.dto.AdminResponse;
import org.jsp.reservationapi.dto.EmailConfiguration;
import org.jsp.reservationapi.dto.ResponseStructure;
import org.jsp.reservationapi.exception.AdminNotFoundException;
import org.jsp.reservationapi.model.Admin;
import org.jsp.reservationapi.util.AccountStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AdminService {
	@Autowired
	private AdminDao adminDao;
	@Autowired
	private ReservationApiMailService mailService;
	@Autowired
	private EmailConfiguration emailConfiguration;
	@Autowired
	private LinkGeneratorService linkGeneratorService;
	
	public ResponseEntity<ResponseStructure<AdminResponse>> saveAdmin(AdminRequest adminRequest, HttpServletRequest request){
//		String siteUrl=request.getRequestURL().toString();
//		String path=request.getServletPath();

		ResponseStructure<AdminResponse> structure = new ResponseStructure<>();
		Admin admin =mapToAdmin(adminRequest);
		admin.setStatus(AccountStatus.IN_ACTIVE.toString());
		adminDao.saveAdmin(admin);
		String activation_link = linkGeneratorService.getActivationLink(admin, request);
		emailConfiguration.setSubject("Activate Your Account");
		emailConfiguration.setText("Dear Admin Please Activate Your Account by cliking on the following link :\n "+activation_link);
		emailConfiguration.setToAddress(admin.getEmail());
	
		structure.setMessage(mailService.sendMail(emailConfiguration));
		structure.setData(mapToAdminResponse(admin));
		structure.setStatusCode(HttpStatus.CREATED.value());
		return ResponseEntity.status(HttpStatus.CREATED).body(structure);
	}
	
	public ResponseEntity<ResponseStructure<AdminResponse>> findById(int id){
		Optional<Admin> recAdmin=adminDao.findById(id);
		if(recAdmin.isPresent()) {
			ResponseStructure<AdminResponse> structure = new ResponseStructure<>();
			structure.setMessage("Admin Saved");
			structure.setData(mapToAdminResponse(recAdmin.get()));
			structure.setStatusCode(HttpStatus.CREATED.value());
			return ResponseEntity.status(HttpStatus.CREATED).body(structure);
		}
		throw new AdminNotFoundException("Admin id is invalid");
	}
	
	public ResponseEntity<ResponseStructure<AdminResponse>> updateAdmin(AdminRequest adminRequest,int id){
		Optional<Admin> recAdmin=adminDao.findById(id);
		if(recAdmin.isPresent()) {
			Admin dbAdmin=recAdmin.get();
			dbAdmin.setEmail(adminRequest.getEmail());
			dbAdmin.setGst_number(adminRequest.getGst_number());
			dbAdmin.setName(adminRequest.getName());
			dbAdmin.setPassword(adminRequest.getPassword());
			dbAdmin.setPhone(adminRequest.getPhone());
			dbAdmin.setTravels_name(adminRequest.getTravels_name());
			ResponseStructure<AdminResponse> structure = new ResponseStructure<>();
			structure.setMessage("Admin Updated");
			structure.setData(mapToAdminResponse(adminDao.saveAdmin(dbAdmin)));
			structure.setStatusCode(HttpStatus.ACCEPTED.value());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(structure);
		}
		throw new AdminNotFoundException("Admin id is invalid");
	}
	
	public ResponseEntity<ResponseStructure<AdminResponse>> verifyAdmin(long phone,String password){
		Optional<Admin> recAdmin =adminDao.verifyAdmin(phone, password);
		if(recAdmin.isPresent()) {
			Admin dbAdmin=recAdmin.get();
			ResponseStructure<AdminResponse> structure = new ResponseStructure<>();
			if(dbAdmin.getStatus().equals(AccountStatus.IN_ACTIVE.toString()))
				throw new IllegalStateException("Please Activate Your Account before login");
			
			structure.setMessage("Admin Verified Successfully");
			structure.setData(mapToAdminResponse(recAdmin.get()));
			structure.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new AdminNotFoundException("Admin phone or password is invalid");
	}
	
	public ResponseEntity<ResponseStructure<AdminResponse>> verifyAdmin(String email,String password){
		Optional<Admin> recAdmin =adminDao.verifyAdmin(email, password);
		if(recAdmin.isPresent()) {
			Admin dbAdmin=recAdmin.get();
			ResponseStructure<AdminResponse> structure = new ResponseStructure<>();
			if(dbAdmin.getStatus().equals(AccountStatus.IN_ACTIVE.toString()))
				throw new IllegalStateException("Please Activate Your Account before login");
			
			structure.setMessage("Admin Verified Successfully");
			structure.setData(mapToAdminResponse(recAdmin.get()));
			structure.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new AdminNotFoundException("Admin Email or Password is invalid");
	}
	
	public ResponseEntity<ResponseStructure<String>> deleteAdmin(int id){
		Optional<Admin> recAdmin =adminDao.findById(id);
		if(recAdmin.isPresent()) {
			ResponseStructure<String> structure = new ResponseStructure<>();
			structure.setMessage("Admin Deleted Successfully");
			structure.setData("Admin Deleted");
			structure.setStatusCode(HttpStatus.OK.value());
			adminDao.deleteAdmin(id);
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new AdminNotFoundException("Admin Email or Password is invalid");
	}
	
	private Admin mapToAdmin(AdminRequest adminRequest) {
		return Admin.builder().email(adminRequest.getEmail()).name(adminRequest.getName()).gst_number(adminRequest.getGst_number())
				.phone(adminRequest.getPhone()).travels_name(adminRequest.getTravels_name()).password(adminRequest.getPassword()).build();
	}
	
	private AdminResponse mapToAdminResponse(Admin admin) {
		return AdminResponse.builder().id(admin.getId()).email(admin.getEmail()).gst_number(admin.getGst_number()).name(admin.getName())
				.password(admin.getPassword()).phone(admin.getPhone()).travels_name(admin.getTravels_name()).build();
	}
	
	public String activate(String token) {
		Optional<Admin> recAdmin=adminDao.findByToken(token);
		if(recAdmin.isEmpty()) 
			throw new AdminNotFoundException("Invalid Token");
		Admin dbAdmin=recAdmin.get();
		dbAdmin.setStatus("Activate");
		dbAdmin.setToken(null);
		adminDao.saveAdmin(dbAdmin);
		return "Your Account has been activated";
	}
	
	public String forgotPassword(String email,HttpServletRequest request) {
		Optional<Admin> recAdmin=adminDao.findByEmail(email);
		if(recAdmin.isEmpty())
			throw new AdminNotFoundException("Invalid email id");
		Admin admin =recAdmin.get();
		String resetPasswordLink=linkGeneratorService.getResetPasswordLink(admin, request);
		emailConfiguration.setToAddress(email);
		emailConfiguration.setText("Please click on the following link to reset your password " + resetPasswordLink);
		emailConfiguration.setSubject("Reset your Password");
		mailService.sendMail(emailConfiguration);
		return "reset password link has been sent to entered mail id";
	}
	
	public ResponseEntity<ResponseStructure<AdminResponse>> verifyLink(String token) {
		Optional<Admin> recAdmin = adminDao.findByToken(token);
		if(recAdmin.isEmpty())
			throw new AdminNotFoundException("Link has been expired or it is invalid");
		Admin dbAdmin=recAdmin.get();
		dbAdmin.setToken(null);
		adminDao.saveAdmin(dbAdmin);
		ResponseStructure<AdminResponse> structure = new ResponseStructure<>();
		structure.setData(mapToAdminResponse(dbAdmin));
		structure.setMessage("Link verified successfull");
		structure.setStatusCode(HttpStatus.OK.value());
		return ResponseEntity.status(HttpStatus.OK).body(structure);
	}
	
}
