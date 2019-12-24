package com.hoangnt.service;

import java.util.List;

import com.hoangnt.model.StadiumDTO;
import com.hoangnt.model.StatusShiftResponse;

public interface StadiumService {
	List<StadiumDTO> getByIdAddress(int id);
	
	List<StadiumDTO> getFullByIdAddress(int id,String date);
	
//	List<StatusShiftResponse> getFullByIdStadiumWithStatus(int id,int status);
	
	List<StatusShiftResponse> notifyConfirmForUser(int idUser,int status);
	
	List<StatusShiftResponse> confirmForManager(int idUser,int status);
	
	int deleteStadium(int id,int idUser);
}
