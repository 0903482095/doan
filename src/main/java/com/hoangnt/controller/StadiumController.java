package com.hoangnt.controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hoangnt.model.StadiumDTO;
import com.hoangnt.model.StatusShiftDTO;
import com.hoangnt.model.StatusShiftResponse;
import com.hoangnt.model.response.Response;
import com.hoangnt.model.response.ResponseData;
import com.hoangnt.service.StadiumService;
import com.hoangnt.service.UserService;

@RestController
public class StadiumController {
	@Autowired
	StadiumService stadiumService;
	
	@Qualifier("manager")
	@Autowired
	UserService managerService;
	
	@Qualifier("user")
	@Autowired
	UserService userService;
	
	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	@GetMapping("/stadiums/all/{id}")
	public ResponseEntity<Response<List<StadiumDTO>>> getStadiumByIdAddress(@PathVariable int id) {
		
		Response<List<StadiumDTO>> response = new Response<>();
	    ResponseData<List<StadiumDTO>> responseData = new ResponseData<>();
	    response.setTimestamp(new Timestamp(System.currentTimeMillis()));
		List<StadiumDTO> stadiumDTOs=stadiumService.getByIdAddress(id);
		responseData.setAddress(stadiumDTOs);
		response.setStatus("OK");
        response.setData(responseData);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	@PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
	@GetMapping("/stadiums/full/address/{id}/date/{date}")
	public ResponseEntity<Response<List<StadiumDTO>>> getFullStadiumByIdAddress(@PathVariable(name = "id") int id,@PathVariable(name = "date") String date) {
		
		Response<List<StadiumDTO>> response = new Response<>();
	    ResponseData<List<StadiumDTO>> responseData = new ResponseData<>();
	    response.setTimestamp(new Timestamp(System.currentTimeMillis()));
		List<StadiumDTO> stadiumDTOs=stadiumService.getFullByIdAddress(id,date);
		responseData.setAddress(stadiumDTOs);
		
		response.setStatus("OK");
        response.setData(responseData);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	@GetMapping("/stadiums/confirm")
	public ResponseEntity<Response<List<StatusShiftResponse>>> confirmShiftStatus() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		int id = managerService.findByUserName(authentication.getName()).getId();

		Response<List<StatusShiftResponse>> response = new Response<>();
		ResponseData<List<StatusShiftResponse>> responseData = new ResponseData<>();
		response.setTimestamp(new Timestamp(System.currentTimeMillis()));
		response.setData(responseData);
		List<StatusShiftResponse> statusShiftResponses=stadiumService.confirmForManager(id, 1);
		responseData.setAddress(statusShiftResponses);
		response.setStatus("OK");
		response.setData(responseData);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}
	
	@PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
	@GetMapping("/stadiums/notify/confirm")
	public ResponseEntity<Response<List<StatusShiftResponse>>> notifyConfirmShiftStatus() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String role=authentication.getAuthorities().toString();
		int id;
		if(role.equals("[ROLE_USER]")) {
			id = userService.findByUserName(authentication.getName()).getId();
		}
		else {
			id = managerService.findByUserName(authentication.getName()).getId();
		}

		Response<List<StatusShiftResponse>> response = new Response<>();
		ResponseData<List<StatusShiftResponse>> responseData = new ResponseData<>();
		response.setTimestamp(new Timestamp(System.currentTimeMillis()));
		response.setData(responseData);
		List<StatusShiftResponse> statusShiftResponses=stadiumService.notifyConfirmForUser(id, 2);
		statusShiftResponses.addAll(stadiumService.notifyConfirmForUser(id, 3));
		responseData.setAddress(statusShiftResponses);
		response.setStatus("OK");
		response.setData(responseData);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}
	
	@PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
	@GetMapping("/stadiums/notify/cancel")
	public ResponseEntity<Response<List<StatusShiftResponse>>> notifyConfirmCancelShiftStatus() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String role=authentication.getAuthorities().toString();
		int id;
		if(role.equals("[ROLE_USER]")) {
			id = userService.findByUserName(authentication.getName()).getId();
		}
		else {
			id = managerService.findByUserName(authentication.getName()).getId();
		}

		Response<List<StatusShiftResponse>> response = new Response<>();
		ResponseData<List<StatusShiftResponse>> responseData = new ResponseData<>();
		response.setTimestamp(new Timestamp(System.currentTimeMillis()));
		response.setData(responseData);
		List<StatusShiftResponse> statusShiftResponses=stadiumService.notifyConfirmForUser(id, 4);
		responseData.setAddress(statusShiftResponses);
		response.setStatus("OK");
		response.setData(responseData);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}
	
//	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
//	@GetMapping("/stadiums/full/address/{id}")
//	public ResponseEntity<Response<List<StadiumDTO>>> getFullStadiumToConfirm(@PathVariable(name = "id") int id) {
//		
//		Response<List<StadiumDTO>> response = new Response<>();
//	    ResponseData<List<StadiumDTO>> responseData = new ResponseData<>();
//	    response.setTimestamp(new Timestamp(System.currentTimeMillis()));
//		List<StadiumDTO> stadiumDTOs=stadiumService.getFullByIdAddress(id,date);
//		responseData.setAddress(stadiumDTOs);
//		
//		response.setStatus("OK");
//        response.setData(responseData);
//		return new ResponseEntity<>(response,HttpStatus.OK);
//	}
//	
	
	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	@DeleteMapping("/stadiums/delete/{id}")
	public ResponseEntity<Response<Void>> deleteStadium(@PathVariable int id) {
		
		Response<Void> response = new Response<>();
	    ResponseData<Void> responseData = new ResponseData<>();
	    response.setTimestamp(new Timestamp(System.currentTimeMillis()));
	    stadiumService.deleteStadium(id);
	   
		response.setStatus("OK");
        response.setData(responseData);
		return new ResponseEntity<>(response,HttpStatus.NO_CONTENT);
	}
}
