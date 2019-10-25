package com.hoangnt.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoangnt.entity.District;
import com.hoangnt.model.DistrictDTO;
import com.hoangnt.model.TownDTO;
import com.hoangnt.repository.DistrictRepository;
import com.hoangnt.service.DistrictService;

@Transactional
@Service
public class DistrictServiceImpl implements DistrictService {

	@Autowired
	DistrictRepository districtRepository;

	@Override
	public DistrictDTO findById(String id) {
		DistrictDTO districtDTO = new DistrictDTO();
		District district = districtRepository.getOne(id);
		districtDTO.setMaqh(district.getMaqh());
		districtDTO.setName(district.getName());
		districtDTO.setType(district.getType());

		List<TownDTO> townDTOs = new ArrayList<>();

		district.getTowns().forEach(town -> {
			TownDTO townDTO = new TownDTO();
			townDTO.setXaid(town.getXaid());
			townDTO.setName(town.getName());
			townDTO.setType(town.getType());

			townDTOs.add(townDTO);
		});
		districtDTO.setTownDTOs(townDTOs);

		return districtDTO;
	}

}
