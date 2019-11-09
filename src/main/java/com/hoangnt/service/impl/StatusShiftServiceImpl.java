package com.hoangnt.service.impl;

import com.hoangnt.entity.Shift;
import com.hoangnt.entity.StatusShift;
import com.hoangnt.entity.User;
import com.hoangnt.model.InformationUser;
import com.hoangnt.model.ShiftDTO;
import com.hoangnt.model.StatusShiftDTO;
import com.hoangnt.model.StatusShiftResponse;
import com.hoangnt.repository.StatusShiftRepository;
import com.hoangnt.service.StatusShiftService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusShiftServiceImpl implements StatusShiftService {
	@Autowired
	StatusShiftRepository statusShiftRepository;

	@Override
	public int addStatusShift(StatusShiftDTO statusShiftDTO) {
		if (checkStatus(statusShiftDTO.getShiftDTO())) {

			StatusShift statusShift = new StatusShift();
			statusShift.setShift(new Shift(statusShiftDTO.getShiftDTO()));
			statusShift.setUser(new User(statusShiftDTO.getUser()));
			statusShift.setDate(statusShiftDTO.getDate());
			statusShift.setStatus(1);
			return statusShiftRepository.save(statusShift).getId();
		}
		return -1;
	}
	
	@Override
	public StatusShiftResponse getAllStatusShiftById(int id) {
		StatusShift statusShift=statusShiftRepository.findById(id).get();
		StatusShiftResponse statusShiftResponse = new StatusShiftResponse();
		statusShiftResponse.setId(statusShift.getId());

		ShiftDTO shiftDTO = new ShiftDTO();
		shiftDTO.setId(statusShift.getShift().getId());
		shiftDTO.setName(statusShift.getShift().getName());
		shiftDTO.setCash(statusShift.getShift().getCash());
		statusShiftResponse.setShiftDTO(shiftDTO);
		
		InformationUser informationUser = new InformationUser();
		informationUser.setId(statusShift.getUser().getId());
		informationUser.setFullName(statusShift.getUser().getFullName());
		informationUser.setEmail(statusShift.getUser().getEmail());
		informationUser.setPhone(statusShift.getUser().getPhone());
		informationUser.setImageURL(statusShift.getUser().getImageURL());

		statusShiftResponse.setUser(informationUser);

		statusShiftResponse.setStatus(statusShift.getStatus());
		return statusShiftResponse;
	}
	
	@Override
	public List<StatusShiftResponse> getAllStatusShiftByDate(String date, int idUser) {

		List<StatusShift> statusShifts = statusShiftRepository.getAllStatusShiftByDate(date);
		List<StatusShiftResponse> statusShiftResponses = new ArrayList<>();

		statusShifts.forEach(statusShift -> {
			if (statusShift.getShift().getStadium().getAddress().getUser().getId() == idUser) {

				StatusShiftResponse statusShiftResponse = new StatusShiftResponse();
				statusShiftResponse.setId(statusShift.getId());

				ShiftDTO shiftDTO = new ShiftDTO();
				shiftDTO.setId(statusShift.getShift().getId());
				shiftDTO.setName(statusShift.getShift().getName());
				shiftDTO.setCash(statusShift.getShift().getCash());
				statusShiftResponse.setShiftDTO(shiftDTO);

				InformationUser informationUser = new InformationUser();
				informationUser.setId(statusShift.getUser().getId());
				informationUser.setFullName(statusShift.getUser().getFullName());
				informationUser.setEmail(statusShift.getUser().getEmail());
				informationUser.setPhone(statusShift.getUser().getPhone());
				informationUser.setImageURL(statusShift.getUser().getImageURL());

				statusShiftResponse.setUser(informationUser);

				statusShiftResponse.setStatus(statusShift.getStatus());
				statusShiftResponses.add(statusShiftResponse);
			}
		});

		return statusShiftResponses;
	}

	public boolean checkStatus(int idShiftDTO) {
		if(null != statusShiftRepository.getStatusShiftByIdShift(idShiftDTO)) return false;
		return true;
	}


}
