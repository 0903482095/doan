package com.hoangnt.service;

import com.hoangnt.model.ShiftDTO;

public interface ShiftService {
	ShiftDTO getShiftById(int id);

	ShiftDTO updateShift(ShiftDTO shiftDTO);
	
	void deleteShift(int id);
}
