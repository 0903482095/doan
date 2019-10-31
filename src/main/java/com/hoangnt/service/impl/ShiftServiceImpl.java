package com.hoangnt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoangnt.entity.Shift;
import com.hoangnt.model.ShiftDTO;
import com.hoangnt.repository.ShiftRepository;
import com.hoangnt.service.ShiftService;

@Service
public class ShiftServiceImpl implements ShiftService{

	@Autowired
	ShiftRepository shiftRepository;
	
	@Override
	public ShiftDTO updateShift(ShiftDTO shiftDTO) {
		Shift shift=shiftRepository.findById(shiftDTO.getId()).get();
		shift.setName(shiftDTO.getName());
		shift.setTime(shiftDTO.getTime());
		shift.setCash(shiftDTO.getCash());
		shiftRepository.save(shift);
		return shiftDTO;
	}
	
}
