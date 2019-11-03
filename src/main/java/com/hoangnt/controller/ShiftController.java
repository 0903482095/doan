package com.hoangnt.controller;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	@PutMapping("/shift/update/{id}")
	public ResponseEntity<Response<ShiftDTO>> updateShift(@RequestBody ShiftDTO shiftDTO,@PathVariable int id) {
		
		Response<ShiftDTO> response = new Response<>();
	    ResponseData<ShiftDTO> responseData = new ResponseData<>();
	    response.setTimestamp(new Timestamp(System.currentTimeMillis()));
	    
	    shiftDTO.setId(id);
		ShiftDTO shift=shiftService.updateShift(shiftDTO);
		responseData.setAddress(shift);
		response.setStatus("OK");
        response.setData(responseData);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	@DeleteMapping("/shift/delete/{id}")
	public ResponseEntity<Response<Void>> deleteShift(@PathVariable int id) {
		
		Response<Void> response = new Response<>();
	    ResponseData<Void> responseData = new ResponseData<>();
	    response.setTimestamp(new Timestamp(System.currentTimeMillis()));
	    
		shiftService.deleteShift(id);
		response.setStatus("OK");
        response.setData(responseData);
		return new ResponseEntity<>(response,HttpStatus.NO_CONTENT);
	}
	
}
