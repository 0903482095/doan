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
		shiftDTO.setTime_start(shift.getTime_start());
		shiftDTO.setTime_end(shift.getTime_end());
		shiftDTO.setCash(shift.getCash());

		return shiftDTO;
	}

	@Override
	public ShiftDTO updateShift(ShiftDTO shiftDTO,int idUser) {
		if(shiftRepository.findById(shiftDTO.getId()).get().getStadium().getAddress().getUser().getId()==idUser) {
			Shift shift=shiftRepository.findById(shiftDTO.getId()).get();
			shift.setName(shiftDTO.getName());
			shift.setTime_start(shiftDTO.getTime_start());
			shift.setTime_end(shiftDTO.getTime_end());
			shift.setCash(shiftDTO.getCash());
			shiftRepository.save(shift);
			return shiftDTO;
		}
		else {
			return null;
		}
	}

	@Override
	public int deleteShift(int id,int idUser) {
		if(shiftRepository.findById(id).get().getStadium().getAddress().getUser().getId()==idUser) {
			shiftRepository.deleteById(id);
			return 1;
		}
		else return -1;
	}
	
}
