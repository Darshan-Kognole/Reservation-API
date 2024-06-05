package org.jsp.reservationapi.repository;


import java.util.Optional;
import org.jsp.reservationapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	public Optional<User> findByPhoneAndPassword(long phone,String password);
	
	public Optional<User> findByEmailAndPassword(String email,String password);

	public Optional<User> findByToken(String token);
}
