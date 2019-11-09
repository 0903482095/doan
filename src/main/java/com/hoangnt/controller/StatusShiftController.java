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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hoangnt.model.StatusShiftDTO;
import com.hoangnt.model.StatusShiftResponse;
import com.hoangnt.model.response.Response;
import com.hoangnt.model.response.ResponseData;
import com.hoangnt.service.StatusShiftService;
import com.hoangnt.service.UserService;

@RestController
public class StatusShiftController {
	@Autowired
	StatusShiftService statusShiftService;

	@Qualifier("user")
	@Autowired
	UserService userService;

	@Qualifier("manager")
	@Autowired
	UserService managerService;

	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN','USER')")
	@PostMapping("/statusshift/create")
	public ResponseEntity<Response<Void>> updateStatusShift(@RequestBody StatusShiftDTO statusShiftDTO) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		int id = userService.findByUserName(authentication.getName()).getId();

		Response<Void> response = new Response<>();
		ResponseData<Void> responseData = new ResponseData<>();
		response.setTimestamp(new Timestamp(System.currentTimeMillis()));
		statusShiftDTO.setUser(id);
		response.setData(responseData);
		if (statusShiftService.addStatusShift(statusShiftDTO) > 0) {
			response.setStatus("OK");

			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} else {
			response.setStatus("That bai");

			return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
		}
	}
	
	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	@GetMapping("/statusshift/{id}")
	public ResponseEntity<Response<StatusShiftResponse>> getStatusShiftByIdShift(@PathVariable int id) {
		
		Response<StatusShiftResponse> response = new Response<>();
		ResponseData<StatusShiftResponse> responseData = new ResponseData<>();
		response.setTimestamp(new Timestamp(System.currentTimeMillis()));

		StatusShiftResponse statusShiftResponse = statusShiftService.getAllStatusShiftById(id);

		responseData.setAddress(statusShiftResponse);
		response.setStatus("OK");
		response.setData(responseData);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}


	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	@GetMapping("/statusshift/date/{date}")
	public ResponseEntity<Response<List<StatusShiftResponse>>> getAllStatusShiftByDate(@PathVariable String date) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		int id = managerService.findByUserName(authentication.getName()).getId();
		
		Response<List<StatusShiftResponse>> response = new Response<>();
		ResponseData<List<StatusShiftResponse>> responseData = new ResponseData<>();
		response.setTimestamp(new Timestamp(System.currentTimeMillis()));

		List<StatusShiftResponse> statusShiftResponses = statusShiftService.getAllStatusShiftByDate(date,id);

		responseData.setAddress(statusShiftResponses);
		response.setStatus("OK");
		response.setData(responseData);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
