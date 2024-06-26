package org.jsp.reservationapi.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.jsp.reservationapi.dao.AdminDao;
import org.jsp.reservationapi.dao.BusDao;
import org.jsp.reservationapi.dto.BusRequest;
import org.jsp.reservationapi.dto.BusResponse;
import org.jsp.reservationapi.dto.ResponseStructure;
import org.jsp.reservationapi.exception.AdminNotFoundException;
import org.jsp.reservationapi.exception.BusNotFoundException;
import org.jsp.reservationapi.exception.UserNotFoundException;
import org.jsp.reservationapi.model.Admin;
import org.jsp.reservationapi.model.Bus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BusService {
	@Autowired
	private BusDao busDao;
	@Autowired
	private AdminDao adminDao;
	
	public ResponseEntity<ResponseStructure<BusResponse>> saveBus(BusRequest busRequest, int admin_id) {
	    Optional<Admin> recAdmin = adminDao.findById(admin_id);
	    if (recAdmin.isPresent()) {
	        Admin admin = recAdmin.get();
	        Bus bus = mapToBus(busRequest);
	        bus.setAdmin(admin); // Assigning Admin to Bus
	        admin.getBuses().add(bus); // Assigning Bus to Admin
	        busDao.saveBus(bus); // Adding Bus to database
	        adminDao.saveAdmin(admin); // Updating Admin

	        ResponseStructure<BusResponse> structure = new ResponseStructure<>();
	        structure.setData(mapToBusResponse(bus)); // Set the bus that was saved
	        structure.setMessage("Bus Saved");
	        structure.setStatusCode(HttpStatus.CREATED.value());
	        return ResponseEntity.status(HttpStatus.CREATED).body(structure);
	    }
	    throw new AdminNotFoundException("Admin id is Invalid ");
	}

	
	public ResponseEntity<ResponseStructure<BusResponse>> findById(int id){
		Optional<Bus> recBus=busDao.findById(id);
		if(recBus.isPresent()) {
			ResponseStructure<BusResponse> structure = new ResponseStructure<>();
			structure.setMessage("Bus Found");
			structure.setData(mapToBusResponse(recBus.get()));
			structure.setStatusCode(HttpStatus.CREATED.value());
			return ResponseEntity.status(HttpStatus.CREATED).body(structure);
		}
		throw new UserNotFoundException("Bus id is invalid");
	}
	
	public ResponseEntity<ResponseStructure<BusResponse>> updateBus(BusRequest busRequest,int id){
		Optional<Bus> recBus=busDao.findById(id);
		if(recBus.isPresent()) {
			Bus dbBus=recBus.get();
			dbBus.setBus_number(busRequest.getBus_number());
			dbBus.setDate_of_departure(busRequest.getDate_of_departure());
			dbBus.setFrom_location(busRequest.getFrom_location());
			dbBus.setName(busRequest.getName());
			dbBus.setNo_of_seat(busRequest.getNo_of_seat());
			dbBus.setTo_location(busRequest.getTo_location());
			ResponseStructure<BusResponse> structure = new ResponseStructure<>();
			structure.setMessage("Bus Updated");
			structure.setData(mapToBusResponse(busDao.saveBus(dbBus)));
			structure.setStatusCode(HttpStatus.ACCEPTED.value());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(structure);
		}
		throw new UserNotFoundException("Bus id is invalid");
	}
	
	public ResponseEntity<ResponseStructure<String>> deleteBus(int id){
		Optional<Bus> recBus =busDao.findById(id);
		if(recBus.isPresent()) {
			Bus bus=recBus.get();
			ResponseStructure<String> structure = new ResponseStructure<>();
			busDao.deleteBus(bus.getId());
			structure.setMessage("Bus Deleted Successfully");
			structure.setData("Bus Deleted");
			structure.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new AdminNotFoundException("Admin Email or Password is invalid");
	}
	
	private Bus mapToBus(BusRequest busRequest) {
		return Bus.builder().bus_number(busRequest.getBus_number()).date_of_departure(busRequest.getDate_of_departure())
				.from_location(busRequest.getFrom_location()).name(busRequest.getName()).no_of_seat(busRequest.getNo_of_seat())
				.to_location(busRequest.getTo_location()).build();
	}
	private BusResponse mapToBusResponse(Bus bus) {
		return BusResponse.builder().id(bus.getId()).bus_number(bus.getBus_number()).date_of_departure(bus.getDate_of_departure())
				.from_location(bus.getFrom_location()).name(bus.getName()).no_of_seat(bus.getNo_of_seat())
				.to_location(bus.getTo_location()).build();
	}
	
	public ResponseEntity<ResponseStructure<List<Bus>>> findAll() {
		ResponseStructure<List<Bus>> structure = new ResponseStructure<>();
		structure.setData(busDao.findAll());
		structure.setMessage("List of All Buses");
		structure.setStatusCode(HttpStatus.OK.value());
		return ResponseEntity.status(HttpStatus.OK).body(structure);
	}
	
	public ResponseEntity<ResponseStructure<List<Bus>>> findBuses(String from, String to, LocalDate dateOfDeparture) {
		ResponseStructure<List<Bus>> structure = new ResponseStructure<>();
		List<Bus> buses = busDao.findBuses(from, to, dateOfDeparture);
		if (buses.isEmpty())
			throw new BusNotFoundException("No Buses for entered route on this Date");
		structure.setData(buses);
		structure.setMessage("List of Buses for entered route on this Date");
		structure.setStatusCode(HttpStatus.OK.value());
		return ResponseEntity.status(HttpStatus.OK).body(structure);
	}
}
