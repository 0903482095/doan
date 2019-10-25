package com.hoangnt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hoangnt.model.TownDTO;
import com.hoangnt.service.TownService;

@RestController
public class TownController {
	@Autowired
	TownService townService;
	
	@GetMapping("location/town/{id}")
	public ResponseEntity<?> getTownById(@PathVariable String id) {
		TownDTO townDTO = townService.findById(id);
		if (townDTO != null) {
			return new ResponseEntity<TownDTO>(townDTO, HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
