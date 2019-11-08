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
	public ShiftDTO getShiftById(int id) {
		Shift shift=shiftRepository.findById(id).get();
		ShiftDTO shiftDTO=new ShiftDTO();
		shiftDTO.setId(shift.getId());
		shiftDTO.setName(shift.getName());
		shiftDTO.setCash(shift.getCash());

		return shiftDTO;
	}

	@Override
	public ShiftDTO updateShift(ShiftDTO shiftDTO) {
		Shift shift=shiftRepository.findById(shiftDTO.getId()).get();
		shift.setName(shift.getName());
		shift.setCash(shiftDTO.getCash());
		shiftRepository.save(shift);
		return shiftDTO;
	}

	@Override
	public void deleteShift(int id) {
		shiftRepository.deleteById(id);
	}
	
}
