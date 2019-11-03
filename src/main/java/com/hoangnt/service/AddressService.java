package com.hoangnt.service;

import java.util.List;

import com.hoangnt.entity.Address;
import com.hoangnt.model.AddressDTO;
import com.hoangnt.model.ShiftDTO;
import com.hoangnt.model.StadiumDTO;
import com.hoangnt.model.StadiumImageDTO;
import com.hoangnt.model.request.RequestAddress;

public interface AddressService {
	int addAddress(RequestAddress requestAddress);
	
	List<AddressDTO> getAll();
	
	List<AddressDTO> getAllByIdUser(int id);
	
	int updateAddress(RequestAddress requestAddress);
		
	void deleteAddress(int id);
	
}
