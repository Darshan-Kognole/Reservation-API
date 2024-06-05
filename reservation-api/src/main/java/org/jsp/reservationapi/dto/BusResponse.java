package org.jsp.reservationapi.dto;

import java.time.LocalDate;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusResponse {
	private int id;
	private String name;
	private LocalDate date_of_departure;
	private String bus_number;
	private String from_location;
	private String to_location;
	private int no_of_seat;
}
