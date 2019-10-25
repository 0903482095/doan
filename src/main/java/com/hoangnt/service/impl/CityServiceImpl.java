package com.hoangnt.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoangnt.entity.City;
import com.hoangnt.model.CityDTO;
import com.hoangnt.model.DistrictDTO;
import com.hoangnt.repository.CityRepository;
import com.hoangnt.service.CityService;

@Transactional
@Service
public class CityServiceImpl implements CityService {

	@Autowired
	CityRepository cityRepository;

	@Override
	public List<CityDTO> findAll() {
		List<CityDTO> cityDTOs = new ArrayList<>();
		cityRepository.findAll().forEach(city -> {

			CityDTO cityDTO = new CityDTO();
			cityDTO.setMatp(city.getMatp());
			cityDTO.setName(city.getName());
			cityDTO.setType(city.getType());

			cityDTOs.add(cityDTO);
		});

		return cityDTOs;
	}

	@Override
	public CityDTO findById(String id) {
		City city = cityRepository.getOne(id);
		CityDTO cityDTO = new CityDTO();
		cityDTO.setMatp(city.getMatp());
		cityDTO.setName(city.getName());
		cityDTO.setType(city.getType());

		List<DistrictDTO> districtDTOs = new ArrayList<>();
		city.getDistricts().forEach(district -> {
			DistrictDTO districtDTO = new DistrictDTO();
			districtDTO.setMaqh(district.getMaqh());
			districtDTO.setName(district.getName());
			districtDTO.setType(district.getType());

			districtDTOs.add(districtDTO);

		});
		cityDTO.setDistrictDTOs(districtDTOs);
		return cityDTO;
	}

}
