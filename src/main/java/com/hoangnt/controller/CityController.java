package com.hoangnt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hoangnt.model.CityDTO;
import com.hoangnt.service.CityService;

@RestController
public class CityController {
	@Autowired
	CityService cityService;

	@GetMapping("location/city") // api tra ve list thanh pho
	public ResponseEntity<List<CityDTO>> getAll() {
		return new ResponseEntity<List<CityDTO>>(cityService.findAll(), HttpStatus.OK);
	}

	@GetMapping("location/city/{id}") // api tra ve thanh pho theo id thanh pho
	public ResponseEntity<?> getProvinceById(@PathVariable String id) {
		CityDTO cityDTO = cityService.findById(id);
		if (cityDTO != null) {
			return new ResponseEntity<CityDTO>(cityDTO, HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
