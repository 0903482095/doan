package com.hoangnt.service.impl;

import com.hoangnt.entity.Address;
import com.hoangnt.entity.Shift;
import com.hoangnt.entity.Stadium;
import com.hoangnt.entity.StatusShift;
import com.hoangnt.entity.User;
import com.hoangnt.model.AddressDTO;
import com.hoangnt.model.InformationUser;
import com.hoangnt.model.ShiftDTO;
import com.hoangnt.model.StadiumDTO;
import com.hoangnt.model.StatusShiftDTO;
import com.hoangnt.model.StatusShiftResponse;
import com.hoangnt.repository.ShiftRepository;
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
	
	@Autowired
	ShiftRepository shiftRepository;

	@Override
	public int addStatusShift(StatusShiftDTO statusShiftDTO) {
		int idManager= shiftRepository.findById(statusShiftDTO.getShiftDTO()).get().getStadium().getAddress().getUser().getId();
		
		if (checkStatus(statusShiftDTO.getShiftDTO(),statusShiftDTO.getDate())) {
			StatusShift statusShift = new StatusShift();
			if(statusShiftDTO.getUser()==idManager) {
				statusShift.setStatus(2);
				statusShift.setUser_confirm(1);
			}
			else {
				statusShift.setStatus(1);
			}
			
			statusShift.setShift(new Shift(statusShiftDTO.getShiftDTO()));
			statusShift.setUser(new User(statusShiftDTO.getUser()));
			statusShift.setDate(statusShiftDTO.getDate());
			return statusShiftRepository.save(statusShift).getId();
		}
		return -1;
	}

	@Override
	public int updateStatusShift(StatusShiftDTO statusShiftDTO,int idUser) {
		if(shiftRepository.findById(statusShiftRepository.findById(statusShiftDTO.getId()).get().getShift().getId()).get().getStadium().getAddress().getUser().getId()==idUser) {
			StatusShift statusShift=statusShiftRepository.findById(statusShiftDTO.getId()).get();
			statusShift.setStatus(statusShiftDTO.getStatus());
			statusShift.setNote(statusShiftDTO.getNote());
			statusShiftRepository.save(statusShift);
			return statusShift.getId();
		}
		return -1;
	}

	@Override
	public StatusShiftResponse getAllStatusShiftById(int id) {
		StatusShift statusShift = statusShiftRepository.findById(id).get();
		StatusShiftResponse statusShiftResponse = new StatusShiftResponse();
		statusShiftResponse.setId(statusShift.getId());

		ShiftDTO shiftDTO = new ShiftDTO();
		shiftDTO.setId(statusShift.getShift().getId());
		shiftDTO.setName(statusShift.getShift().getName());
		shiftDTO.setTime_start(statusShift.getShift().getTime_start());
		shiftDTO.setTime_end(statusShift.getShift().getTime_end());
		shiftDTO.setCash(statusShift.getShift().getCash());
		statusShiftResponse.setShiftDTO(shiftDTO);
		
		AddressDTO addressDTO=new AddressDTO();
		Address address=statusShift.getShift().getStadium().getAddress();
		addressDTO.setId(address.getId());
		addressDTO.setName(address.getName());
		statusShiftResponse.setAddressDTO(addressDTO);
		
		StadiumDTO stadiumDTO=new StadiumDTO();
		Stadium stadium=statusShift.getShift().getStadium();
		stadiumDTO.setId(stadium.getId());
		stadiumDTO.setName(stadium.getName());
		statusShiftResponse.setStadiumDTO(stadiumDTO);

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
	public List<StatusShiftResponse> getStatusShiftByStatusAndUser(int status, int idUser) {
		List<StatusShift> statusShifts = statusShiftRepository.getStatusShiftByStatusAndUser(status, idUser);
		List<StatusShiftResponse> statusShiftResponses=new ArrayList<>();
		
		statusShifts.forEach(statusShift->{
			StatusShiftResponse statusShiftResponse = new StatusShiftResponse();
			statusShiftResponse.setId(statusShift.getId());

			ShiftDTO shiftDTO = new ShiftDTO();
			shiftDTO.setId(statusShift.getShift().getId());
			shiftDTO.setName(statusShift.getShift().getName());
			shiftDTO.setTime_start(statusShift.getShift().getTime_start());
			shiftDTO.setTime_end(statusShift.getShift().getTime_end());
			shiftDTO.setCash(statusShift.getShift().getCash());
			statusShiftResponse.setShiftDTO(shiftDTO);
			
			AddressDTO addressDTO=new AddressDTO();
			Address address=statusShift.getShift().getStadium().getAddress();
			addressDTO.setId(address.getId());
			addressDTO.setName(address.getName());
			statusShiftResponse.setAddressDTO(addressDTO);
			
			StadiumDTO stadiumDTO=new StadiumDTO();
			Stadium stadium=statusShift.getShift().getStadium();
			stadiumDTO.setId(stadium.getId());
			stadiumDTO.setName(stadium.getName());
			statusShiftResponse.setStadiumDTO(stadiumDTO);

			statusShiftResponse.setStatus(statusShift.getStatus());
			
			statusShiftResponses.add(statusShiftResponse);
		});
		
		return statusShiftResponses;
	}
	

//	@Override
//	public List<StatusShiftResponse> getAllStatusShiftByDate(String date, int idUser) {
//
//		List<StatusShift> statusShifts = statusShiftRepository.getAllStatusShiftByDate(date);
//		List<StatusShiftResponse> statusShiftResponses = new ArrayList<>();
//
//		statusShifts.forEach(statusShift -> {
//			if (statusShift.getShift().getStadium().getAddress().getUser().getId() == idUser) {
//
//				StatusShiftResponse statusShiftResponse = new StatusShiftResponse();
//				statusShiftResponse.setId(statusShift.getId());
//
//				ShiftDTO shiftDTO = new ShiftDTO();
//				shiftDTO.setId(statusShift.getShift().getId());
//				shiftDTO.setName(statusShift.getShift().getName());
//				shiftDTO.setTime_start(statusShift.getShift().getTime_start());
//				shiftDTO.setTime_end(statusShift.getShift().getTime_end());
//				shiftDTO.setCash(statusShift.getShift().getCash());
//				statusShiftResponse.setShiftDTO(shiftDTO);
//
//				InformationUser informationUser = new InformationUser();
//				informationUser.setId(statusShift.getUser().getId());
//				informationUser.setFullName(statusShift.getUser().getFullName());
//				informationUser.setEmail(statusShift.getUser().getEmail());
//				informationUser.setPhone(statusShift.getUser().getPhone());
//				informationUser.setImageURL(statusShift.getUser().getImageURL());
//
//				statusShiftResponse.setUser(informationUser);
//
//				statusShiftResponse.setStatus(statusShift.getStatus());
//				statusShiftResponses.add(statusShiftResponse);
//			}
//		});
//
//		return statusShiftResponses;
//	}
	
	@Override
	public void changeStatusNotifyConfirm(int idStatusShift,int idUser) {
		StatusShift statusShift=statusShiftRepository.findById(idStatusShift).get();
		statusShift.setUser_confirm(1);
		statusShiftRepository.save(statusShift);
	}

	public boolean checkStatus(int idShiftDTO, String date) {
		if (null != statusShiftRepository.getStatusShiftByIdShiftAndDate(idShiftDTO, date)) {
			StatusShift statusShift=statusShiftRepository.getStatusShiftByIdShiftAndDate(idShiftDTO, date);
			if (statusShift.getStatus()==1 || statusShift.getStatus()==2 || statusShift.getStatus()==3)
				return false;
		}
		return true;
	}

}
