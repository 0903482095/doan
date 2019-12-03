package com.hoangnt.service;

import java.util.List;

import com.hoangnt.model.StatusShiftDTO;
import com.hoangnt.model.StatusShiftResponse;

public interface StatusShiftService {
	int addStatusShift(StatusShiftDTO statusShiftDTO);
	
	int updateStatusShift(StatusShiftDTO statusShiftDTO);
	
	StatusShiftResponse getAllStatusShiftById(int id);
	
	List<StatusShiftResponse> getAllStatusShiftByDate(String date,int idUser);
	
	void changeStatusNotifyConfirm(int idStatusShift);
}
