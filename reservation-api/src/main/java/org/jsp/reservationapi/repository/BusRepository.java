package org.jsp.reservationapi.repository;

import java.time.LocalDate;
import java.util.List;

import org.jsp.reservationapi.model.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BusRepository extends JpaRepository<Bus, Integer>{
	
	@Query("SELECT b FROM Bus b WHERE b.from_location = :from_location AND b.to_location = :to_location AND b.date_of_departure = :date_of_departure")
    List<Bus> findBuses(String from_location, String to_location, LocalDate date_of_departure);
}
