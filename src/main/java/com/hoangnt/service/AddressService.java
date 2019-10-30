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
	
	AddressDTO getAddressById(int id);
	
	List<AddressDTO> getAll();
	
	int updateAddress(RequestAddress requestAddress);
	
	int addImage(List<String> stadiumImageDTO,int idAddress);
	
	int updateShift(ShiftDTO shiftDTO);
	
	void deleteAddress(int id);
	
}
