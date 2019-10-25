package com.hoangnt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hoangnt.model.DistrictDTO;
import com.hoangnt.service.DistrictService;

@RestController
public class DistrictController {
	@Autowired
	DistrictService districtService;

	@GetMapping("location/district/{id}")  // api tra ve huyen theo ma huyen
	public ResponseEntity<?> getDistrictById(@PathVariable String id) {
		DistrictDTO districtDTO = districtService.findById(id);
		if (districtDTO != null) {
			return new ResponseEntity<DistrictDTO>(districtDTO, HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
