package com.hoangnt.service;

import java.util.List;

import com.hoangnt.model.CityDTO;

public interface CityService {
	List<CityDTO> findAll();

	CityDTO findById(String id);
}
