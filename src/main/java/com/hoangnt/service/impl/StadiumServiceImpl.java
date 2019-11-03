package com.hoangnt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoangnt.model.ShiftDTO;
import com.hoangnt.model.StadiumDTO;
import com.hoangnt.repository.StadiumRepository;
import com.hoangnt.service.StadiumService;
import com.hoangnt.utils.Status;
import com.hoangnt.utils.TypeStadium;

@Service
public class StadiumServiceImpl implements StadiumService{
	
	@Autowired
	StadiumRepository stadiumRepository;

	@Override
	public List<StadiumDTO> getByIdAddress(int id) {
		List<StadiumDTO> stadiumDTOs = new ArrayList<StadiumDTO>();
		stadiumRepository.getByIdAddress(id).forEach(stadium -> {
			StadiumDTO stadiumDTO = new StadiumDTO();
			stadiumDTO.setId(stadium.getId());
			stadiumDTO.setName(stadium.getName());
			stadiumDTO.setMaType(stadium.getType());
			stadiumDTO.setType(TypeStadium.getTypeByValue(stadium.getType()).toString());
			stadiumDTO.setDescription(stadium.getDescription());
		
//			List<ShiftDTO> shiftDTOs = new ArrayList<>();
//			stadium.getShifts().forEach(shift -> {
//				ShiftDTO shiftDTO = new ShiftDTO();
//				shiftDTO.setId(shift.getId());
//				shiftDTO.setName(shift.getName());
//				shiftDTO.setCash(shift.getCash());
//				shiftDTOs.add(shiftDTO);
//			});
//			stadiumDTO.setShiftDTOs(shiftDTOs);
			stadiumDTOs.add(stadiumDTO);
		});
		return stadiumDTOs;
	}

	@Override
	public List<StadiumDTO> getFullByIdAddress(int id) {
		List<StadiumDTO> stadiumDTOs = new ArrayList<StadiumDTO>();
		stadiumRepository.getByIdAddress(id).forEach(stadium -> {
			StadiumDTO stadiumDTO = new StadiumDTO();
			stadiumDTO.setId(stadium.getId());
			stadiumDTO.setName(stadium.getName());
			stadiumDTO.setMaType(stadium.getType());
			stadiumDTO.setType(TypeStadium.getTypeByValue(stadium.getType()).toString());
			stadiumDTO.setDescription(stadium.getDescription());
		
			List<ShiftDTO> shiftDTOs = new ArrayList<>();
			stadium.getShifts().forEach(shift -> {
				ShiftDTO shiftDTO = new ShiftDTO();
				shiftDTO.setId(shift.getId());
				shiftDTO.setName(shift.getName());
				shiftDTO.setCash(shift.getCash());
				shiftDTO.setStatus(shift.getStatus());
				shiftDTO.setNameStatus(Status.getStatusByValue(shift.getStatus()).toString());
				shiftDTOs.add(shiftDTO);
			});
			stadiumDTO.setShiftDTOs(shiftDTOs);
			stadiumDTOs.add(stadiumDTO);
		});
		return stadiumDTOs;
	}

	@Override
	public void deleteStadium(int id) {
		stadiumRepository.deleteById(id);
		
	}

}
