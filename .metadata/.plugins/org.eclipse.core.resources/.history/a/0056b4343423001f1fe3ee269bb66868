package org.jsp.reservationapi.repository;

import java.util.Optional;

import org.jsp.reservationapi.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminRepository extends JpaRepository<Admin, Integer>{
	
	public Optional<Admin> findByPhoneAndPassword(long phone,String password);
	
	public Optional<Admin> findByEmailAndPassword(String email,String password);
	
	public Optional<Admin> findByToken(String token);
}
