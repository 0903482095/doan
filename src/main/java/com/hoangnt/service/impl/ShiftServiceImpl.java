package com.hoangnt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoangnt.entity.Shift;
import com.hoangnt.model.ShiftDTO;
import com.hoangnt.repository.ShiftRepository;
import com.hoangnt.service.ShiftService;
import com.hoangnt.utils.Status;

@Service
public class ShiftServiceImpl implements ShiftService{

	@Autowired
	ShiftRepository shiftRepository;
	
	@Override
	public ShiftDTO updateShift(ShiftDTO shiftDTO) {
		Shift shift=shiftRepository.findById(shiftDTO.getId()).get();
		shift.setName(shift.getName());
		shift.setCash(shiftDTO.getCash());
		shift.setStatus(shiftDTO.getStatus());
		shiftRepository.save(shift);
		shiftDTO.setNameStatus(Status.getStatusByValue(shiftDTO.getStatus()).toString());
		return shiftDTO;
	}

	@Override
	public void deleteShift(int id) {
		shiftRepository.deleteById(id);
	}
	
}
