package com.hoangnt.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

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
		requestAddress.setId(0);
		int idAddress=addressService.addAddress(requestAddress);
		responseData.setAddress(idAddress);
		response.setStatus("OK");
        response.setData(responseData);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	@GetMapping("/address/info/{id}")
	public ResponseEntity<Response<AddressDTO>> getInfoAddress(@PathVariable int id) {
		
		Response<AddressDTO> response = new Response<>();
	    ResponseData<AddressDTO> responseData = new ResponseData<>();
	    response.setTimestamp(new Timestamp(System.currentTimeMillis()));
		AddressDTO addressDTO=addressService.getAddressById(id);
		responseData.setAddress(addressDTO);
		response.setStatus("OK");
        response.setData(responseData);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	@GetMapping("/addresss")
	public ResponseEntity<Response<List<AddressDTO>>> getAll() {
		
		Response<List<AddressDTO>> response = new Response<>();
	    ResponseData<List<AddressDTO>> responseData = new ResponseData<>();
	    response.setTimestamp(new Timestamp(System.currentTimeMillis()));
		List<AddressDTO> addressDTOs=addressService.getAll();
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
	    int idAddress=addressService.addAddress(requestAddress);
		responseData.setAddress(idAddress);
		response.setStatus("OK");
        response.setData(responseData);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	@PutMapping("/address/add/image/{id}")
	public ResponseEntity<Response<Integer>> updateImageAddress(@RequestParam("images") MultipartFile[] files,@PathVariable int id) throws IOException {
		List<String> urls=new ArrayList<String>();
		
		
		Response<Integer> response = new Response<>();
	    ResponseData<Integer> responseData = new ResponseData<>();
	    response.setTimestamp(new Timestamp(System.currentTimeMillis()));
	    UploadImage uploadImage=new UploadImage();
	    for(int i=0;i<files.length;i++) {
	    	String nameImage=uploadImage.ramdom() + files[i].getOriginalFilename();
	    	urls.add("http://vuonxa.com:9090/resources/upload-dir/address/" + nameImage);
	    	uploadImage.store(files[i], nameImage,rootLocation);
	    }
	    
		int idAddress=addressService.addImage(urls, id);
		responseData.setAddress(idAddress);
		response.setStatus("OK");
        response.setData(responseData);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	//upload file

    private final Path rootLocation = Paths.get(Paths.get("").toAbsolutePath() + "/src/main/resources/upload-dir/address/");
}
