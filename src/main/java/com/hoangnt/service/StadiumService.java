package com.hoangnt.service;

import java.util.List;

import com.hoangnt.model.StadiumDTO;

public interface StadiumService {
	List<StadiumDTO> getByIdAddress(int id);
	
	List<StadiumDTO> getFullByIdAddress(int id,String date);
	
	void deleteStadium(int id);
}
