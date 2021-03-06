package com.hoangnt.service;

import java.util.List;

import com.hoangnt.entity.Address;
import com.hoangnt.model.AddressDTO;
import com.hoangnt.model.StatisticalAll;
import com.hoangnt.model.ShiftDTO;
import com.hoangnt.model.StadiumDTO;
import com.hoangnt.model.StadiumImageDTO;
import com.hoangnt.model.request.RequestAddress;

public interface AddressService {
	int addAddress(RequestAddress requestAddress);
	
	List<AddressDTO> getAll();
	
	List<AddressDTO> getAllByIdUser(int id);
	
	//List<AddressDTO> getAllByIdUserWithStatus(int id,int status);
	
	int updateAddress(RequestAddress requestAddress,int idUser);
		
	int deleteAddress(int id,int idUser);
	
	List<AddressDTO> getListAddressByLatLng(double lat,double lng);
	
	StatisticalAll<Float> profitDateAddressByIdUserWithStatus(int id,int status,String date);
	
	StatisticalAll<Integer> numberShiftDateAddressByIdUserWithStatus(int id,int status,String date);
	
	StatisticalAll<Integer> numberShiftDateAddressByIdUserWithStatus0(int id,String date);
	
}
