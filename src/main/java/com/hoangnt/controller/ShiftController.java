package com.hoangnt.controller;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.hoangnt.model.ShiftDTO;
import com.hoangnt.model.response.Response;
import com.hoangnt.model.response.ResponseData;
import com.hoangnt.service.ShiftService;
import com.hoangnt.service.UserService;

@RestController
public class ShiftController {
	@Autowired
	ShiftService shiftService;
	
	@Qualifier("manager")
	@Autowired
	UserService managerService;
	
	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	@GetMapping("/shift/{id}")
	public ResponseEntity<Response<ShiftDTO>> getShift(@PathVariable int id) {
		
		Response<ShiftDTO> response = new Response<>();
	    ResponseData<ShiftDTO> responseData = new ResponseData<>();
	    response.setTimestamp(new Timestamp(System.currentTimeMillis()));
	    
		ShiftDTO shift=shiftService.getShiftById(id);
		responseData.setAddress(shift);
		response.setStatus("OK");
        response.setData(responseData);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	@PutMapping("/shift/update/{id}")
	public ResponseEntity<Response<ShiftDTO>> updateShift(@RequestBody ShiftDTO shiftDTO,@PathVariable int id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		int idUser = managerService.findByUserName(authentication.getName()).getId();

		Response<ShiftDTO> response = new Response<>();
		ResponseData<ShiftDTO> responseData = new ResponseData<>();
		response.setTimestamp(new Timestamp(System.currentTimeMillis()));

		shiftDTO.setId(id);
		ShiftDTO shift=shiftService.updateShift(shiftDTO,idUser);
		if(shift!=null) {
			responseData.setAddress(shift);
			response.setStatus("OK");
			response.setData(responseData);
			return new ResponseEntity<>(response,HttpStatus.CREATED);
		}
		else {
			response.setStatus("UPDATE FAIL");
			response.setData(responseData);
			return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	@DeleteMapping("/shift/delete/{id}")
	public ResponseEntity<Response<Void>> deleteShift(@PathVariable int id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		int idUser = managerService.findByUserName(authentication.getName()).getId();
		
		Response<Void> response = new Response<>();
	    ResponseData<Void> responseData = new ResponseData<>();
	    response.setTimestamp(new Timestamp(System.currentTimeMillis()));
	    
		int status=shiftService.deleteShift(id,idUser);
		if(status>0) {
			response.setStatus("OK");
	        response.setData(responseData);
			return new ResponseEntity<>(response,HttpStatus.NO_CONTENT);
		}
		else {
			response.setStatus("DELETE FAIL");
	        response.setData(responseData);
			return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		}
	}
	
}
