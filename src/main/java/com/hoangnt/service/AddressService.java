package com.hoangnt.service;

import java.util.List;

import com.hoangnt.entity.Address;
import com.hoangnt.model.AddressDTO;
import com.hoangnt.model.request.RequestAddress;

public interface AddressService {
	int addAddress(RequestAddress requestAddress);
	
	AddressDTO getAddressById(int id);
	
	List<AddressDTO> getAll();
	
}
