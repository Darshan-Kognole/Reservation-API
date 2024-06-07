package org.jsp.reservationapi.dto;

import java.time.LocalDate;

import org.jsp.reservationapi.model.Admin;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BusRequest {
	private int id;
	@NotBlank(message = "Name is manadatory")
	private String name;
	private LocalDate date_of_departure;
	@NotBlank(message = "Bus number is manadatory")
	private String bus_number;
	@NotBlank(message = "From location is manadatory")
	private String from_location;
	@NotBlank(message = "to location is manadatory")
	private String to_location;
	private int no_of_seat;
	private int cost_per_seat;
	private Admin admin;
}
