package com.hoangnt.controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hoangnt.model.StadiumDTO;
import com.hoangnt.model.response.Response;
import com.hoangnt.model.response.ResponseData;
import com.hoangnt.service.StadiumService;

@RestController
public class StadiumController {
	@Autowired
	StadiumService stadiumService;
	
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
	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	@GetMapping("/stadiums/full/{id}")
	public ResponseEntity<Response<List<StadiumDTO>>> getFullStadiumByIdAddress(@PathVariable int id) {
		
		Response<List<StadiumDTO>> response = new Response<>();
	    ResponseData<List<StadiumDTO>> responseData = new ResponseData<>();
	    response.setTimestamp(new Timestamp(System.currentTimeMillis()));
		List<StadiumDTO> stadiumDTOs=stadiumService.getFullByIdAddress(id);
		responseData.setAddress(stadiumDTOs);
		
		response.setStatus("OK");
        response.setData(responseData);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
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
