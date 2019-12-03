package com.hoangnt.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RestController;

import com.hoangnt.model.AddressDTO;
import com.hoangnt.model.StatisticalAll;
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
	
	@Qualifier("user")
	@Autowired
	UserService user;

	@Autowired
	AddressService addressService;

	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	@PostMapping("/address/register")
	public ResponseEntity<Response<Integer>> addAddress(@RequestBody RequestAddress requestAddress) {

		Response<Integer> response = new Response<>();
		ResponseData<Integer> responseData = new ResponseData<>();
		response.setTimestamp(new Timestamp(System.currentTimeMillis()));

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		int id = userService.findByUserName(authentication.getName()).getId();
		requestAddress.setUser(id);
		int idAddress = addressService.addAddress(requestAddress);
		responseData.setAddress(idAddress);
		response.setStatus("OK");
		response.setData(responseData);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	@GetMapping("/address/all")
	public ResponseEntity<Response<List<AddressDTO>>> getAllByIdUser() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		int id = userService.findByUserName(authentication.getName()).getId();

		Response<List<AddressDTO>> response = new Response<>();
		ResponseData<List<AddressDTO>> responseData = new ResponseData<>();
		response.setTimestamp(new Timestamp(System.currentTimeMillis()));
		List<AddressDTO> addressDTOs = addressService.getAllByIdUser(id);
		responseData.setAddress(addressDTOs);
		response.setStatus("OK");
		response.setData(responseData);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	@GetMapping("/address/confirm")
	public ResponseEntity<Response<List<AddressDTO>>> getAllWithStatus() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		int id = userService.findByUserName(authentication.getName()).getId();

		Response<List<AddressDTO>> response = new Response<>();
		ResponseData<List<AddressDTO>> responseData = new ResponseData<>();
		response.setTimestamp(new Timestamp(System.currentTimeMillis()));
		List<AddressDTO> addressDTOs = addressService.getAllByIdUserWithStatus(id, 1);
		responseData.setAddress(addressDTOs);
		response.setStatus("OK");
		response.setData(responseData);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	

	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	@PutMapping("/address/update/{id}")
	public ResponseEntity<Response<Integer>> updateAddress(@RequestBody RequestAddress requestAddress,
			@PathVariable int id) {

		Response<Integer> response = new Response<>();
		ResponseData<Integer> responseData = new ResponseData<>();
		response.setTimestamp(new Timestamp(System.currentTimeMillis()));

		requestAddress.setId(id);
		int idAddress = addressService.updateAddress(requestAddress);
		responseData.setAddress(idAddress);
		response.setStatus("OK");
		response.setData(responseData);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	@DeleteMapping("/address/delete/{id}")
	public ResponseEntity<Response<Void>> deleteAddress(@PathVariable int id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		int idUser = userService.findByUserName(authentication.getName()).getId();

		Response<Void> response = new Response<>();
		ResponseData<Void> responseData = new ResponseData<>();
		response.setTimestamp(new Timestamp(System.currentTimeMillis()));
		response.setData(responseData);
		if (addressService.deleteAddress(id, idUser) > 0) {
			response.setStatus("OK");

			return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
		} else {
			response.setStatus("Fail");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}	
	
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@GetMapping(value = {"/address/coordinate/{lat}/{lng}","/address/coordinate"})
	public ResponseEntity<Response<List<AddressDTO>>> getAllByLatLng(@PathVariable Optional<Double> lat,@PathVariable Optional<Double> lng) {

		Response<List<AddressDTO>> response = new Response<>();
		ResponseData<List<AddressDTO>> responseData = new ResponseData<>();
		response.setTimestamp(new Timestamp(System.currentTimeMillis()));
		List<AddressDTO> addressDTOs;
		if(lat.isPresent()) {
			addressDTOs= addressService.getListAddressByLatLng(lat.get(), lng.get());
		}
		else {
			addressDTOs= addressService.getAll();
		}
		responseData.setAddress(addressDTOs);
		response.setStatus("OK");
		response.setData(responseData);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	@GetMapping("/address/statistical/profit/real/{date}")
	public ResponseEntity<Response<StatisticalAll<Float>>> profitReal(@PathVariable String date) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		int id = userService.findByUserName(authentication.getName()).getId();

		Response<StatisticalAll<Float>> response = new Response<>();
		ResponseData<StatisticalAll<Float>> responseData = new ResponseData<>();
		response.setTimestamp(new Timestamp(System.currentTimeMillis()));
		StatisticalAll<Float> statisticalAll = addressService.profitDateAddressByIdUserWithStatus(id, 3, date);
		responseData.setStatistical(statisticalAll);
		response.setStatus("OK");
		response.setData(responseData);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	@GetMapping("/address/statistical/profit/fake/{date}")
	public ResponseEntity<Response<StatisticalAll<Float>>> profitFake(@PathVariable String date) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		int id = userService.findByUserName(authentication.getName()).getId();

		Response<StatisticalAll<Float>> response = new Response<>();
		ResponseData<StatisticalAll<Float>> responseData = new ResponseData<>();
		response.setTimestamp(new Timestamp(System.currentTimeMillis()));
		StatisticalAll<Float> statisticalAll = addressService.profitDateAddressByIdUserWithStatus(id, 2, date);
		responseData.setStatistical(statisticalAll);
		response.setStatus("OK");
		response.setData(responseData);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	@GetMapping("/address/statistical/numberStatus/{status}/{date}")
	public ResponseEntity<Response<StatisticalAll<Integer>>> numberShiftWithStatus(@PathVariable String date,@PathVariable int status) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		int id = userService.findByUserName(authentication.getName()).getId();

		Response<StatisticalAll<Integer>> response = new Response<>();
		ResponseData<StatisticalAll<Integer>> responseData = new ResponseData<>();
		response.setTimestamp(new Timestamp(System.currentTimeMillis()));
		StatisticalAll<Integer> statisticalAll = addressService.numberShiftDateAddressByIdUserWithStatus(id, status, date);
		responseData.setStatistical(statisticalAll);
		response.setStatus("OK");
		response.setData(responseData);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	@GetMapping("/address/statistical/numberStatus/{date}")
	public ResponseEntity<Response<StatisticalAll<Integer>>> numberShiftWithStatus0(@PathVariable String date) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		int id = userService.findByUserName(authentication.getName()).getId();

		Response<StatisticalAll<Integer>> response = new Response<>();
		ResponseData<StatisticalAll<Integer>> responseData = new ResponseData<>();
		response.setTimestamp(new Timestamp(System.currentTimeMillis()));
		StatisticalAll<Integer> statisticalAll = addressService.numberShiftDateAddressByIdUserWithStatus0(id, date);
		responseData.setStatistical(statisticalAll);
		response.setStatus("OK");
		response.setData(responseData);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
