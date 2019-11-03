package com.hoangnt.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hoangnt.model.AddressDTO;
import com.hoangnt.model.request.RequestAddress;
import com.hoangnt.model.response.Response;
import com.hoangnt.model.response.ResponseData;
import com.hoangnt.service.AddressService;
import com.hoangnt.service.UserService;
import com.hoangnt.utils.UploadImage;

@RestController
public class AddressController {
	@Qualifier("manager")
	@Autowired
	UserService userService;

	@Autowired
	AddressService addressService;
	
	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	@PostMapping("/address/register")
	public ResponseEntity<Response<Integer>> addAddress(@RequestBody RequestAddress requestAddress) {
		
		Response<Integer> response = new Response<>();
	    ResponseData<Integer> responseData = new ResponseData<>();
	    response.setTimestamp(new Timestamp(System.currentTimeMillis()));
	    
		Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
		int id=userService.findByUserName(authentication.getName()).getId();
		requestAddress.setUser(id);
		int idAddress=addressService.addAddress(requestAddress);
		responseData.setAddress(idAddress);
		response.setStatus("OK");
        response.setData(responseData);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	@GetMapping("/address/all")
	public ResponseEntity<Response<List<AddressDTO>>> getAll() {
		
		Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
		int id=userService.findByUserName(authentication.getName()).getId();
		
		Response<List<AddressDTO>> response = new Response<>();
	    ResponseData<List<AddressDTO>> responseData = new ResponseData<>();
	    response.setTimestamp(new Timestamp(System.currentTimeMillis()));
		List<AddressDTO> addressDTOs=addressService.getAllByIdUser(id);
		responseData.setAddress(addressDTOs);
		response.setStatus("OK");
        response.setData(responseData);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	@PutMapping("/address/update/{id}")
	public ResponseEntity<Response<Integer>> updateAddress(@RequestBody RequestAddress requestAddress,@PathVariable int id) {
		
		Response<Integer> response = new Response<>();
	    ResponseData<Integer> responseData = new ResponseData<>();
	    response.setTimestamp(new Timestamp(System.currentTimeMillis()));
	    
	    requestAddress.setId(id);
	    int idAddress=addressService.updateAddress(requestAddress);
		responseData.setAddress(idAddress);
		response.setStatus("OK");
        response.setData(responseData);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	@DeleteMapping("/address/delete/{id}")
	public ResponseEntity<Response<Void>> deleteAddress(@PathVariable int id) {
		
		Response<Void> response = new Response<>();
	    ResponseData<Void> responseData = new ResponseData<>();
	    response.setTimestamp(new Timestamp(System.currentTimeMillis()));
	    addressService.deleteAddress(id);
	   
		response.setStatus("OK");
        response.setData(responseData);
		return new ResponseEntity<>(response,HttpStatus.NO_CONTENT);
	}
}
