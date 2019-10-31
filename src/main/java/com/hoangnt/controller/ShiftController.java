package com.hoangnt.controller;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hoangnt.model.ShiftDTO;
import com.hoangnt.model.response.Response;
import com.hoangnt.model.response.ResponseData;
import com.hoangnt.service.ShiftService;

@RestController
public class ShiftController {
	@Autowired
	ShiftService shiftService;
	
	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	@PutMapping("/shift/update")
	public ResponseEntity<Response<ShiftDTO>> addAddress(@RequestBody ShiftDTO shiftDTO) {
		
		Response<ShiftDTO> response = new Response<>();
	    ResponseData<ShiftDTO> responseData = new ResponseData<>();
	    response.setTimestamp(new Timestamp(System.currentTimeMillis()));
	    
		ShiftDTO shift=shiftService.updateShift(shiftDTO);
		responseData.setAddress(shift);
		response.setStatus("OK");
        response.setData(responseData);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
}
