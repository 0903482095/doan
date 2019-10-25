package com.hoangnt.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoangnt.entity.Town;
import com.hoangnt.model.TownDTO;
import com.hoangnt.repository.TownRepository;
import com.hoangnt.service.TownService;

@Transactional
@Service
public class TownServiceImpl implements TownService {

	@Autowired
	TownRepository townRepository;

	@Override
	public TownDTO findById(String id) {
		TownDTO townDTO = new TownDTO();
		Town town = townRepository.getOne(id);

		townDTO.setXaid(town.getXaid());
		townDTO.setName(town.getName());
		townDTO.setType(town.getType());
		return townDTO;
	}

}
