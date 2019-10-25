package com.hoangnt.controller;

import com.hoangnt.model.request.RequestAddress;
import com.hoangnt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hoangnt.model.AddressDTO;
import com.hoangnt.service.AddressService;

@RestController
public class AddressController {
	@Qualifier("manager")
	@Autowired
	UserService userService;

	@Autowired
	AddressService addressService;
	
	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	@PostMapping("/address/register")
	public ResponseEntity<Void> addUser(@RequestBody RequestAddress requestAddress) {
		Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
		int id=userService.findByUserName(authentication.getName()).getId();

		requestAddress.setUser(id);
		addressService.addAddress(requestAddress);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
}
