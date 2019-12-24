package com.hoangnt.service;

import com.hoangnt.model.ShiftDTO;

public interface ShiftService {
	ShiftDTO getShiftById(int id);

	ShiftDTO updateShift(ShiftDTO shiftDTO,int idUser);
	
	int deleteShift(int id, int idUser);
}
