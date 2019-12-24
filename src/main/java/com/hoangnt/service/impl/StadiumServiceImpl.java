package com.hoangnt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoangnt.entity.Shift;
import com.hoangnt.entity.StatusShift;
import com.hoangnt.model.AddressDTO;
import com.hoangnt.model.InformationUser;
import com.hoangnt.model.ShiftDTO;
import com.hoangnt.model.StadiumDTO;
import com.hoangnt.model.StatusShiftResponse;
import com.hoangnt.repository.StadiumRepository;
import com.hoangnt.repository.StatusShiftRepository;
import com.hoangnt.service.StadiumService;
import com.hoangnt.utils.Status;
import com.hoangnt.utils.TypeStadium;

@Service
public class StadiumServiceImpl implements StadiumService {

	@Autowired
	StadiumRepository stadiumRepository;

	@Autowired
	StatusShiftRepository statusShiftRepository;

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

			stadiumDTOs.add(stadiumDTO);
		});
		return stadiumDTOs;
	}

	@Override
	public List<StadiumDTO> getFullByIdAddress(int id, String date) {
		List<StadiumDTO> stadiumDTOs = new ArrayList<StadiumDTO>();
		stadiumRepository.getByIdAddress(id).forEach(stadium -> {
			StadiumDTO stadiumDTO = new StadiumDTO();
			stadiumDTO.setId(stadium.getId());
			stadiumDTO.setName(stadium.getName());
			stadiumDTO.setMaType(stadium.getType());
			stadiumDTO.setType(TypeStadium.getTypeByValue(stadium.getType()).toString());
			stadiumDTO.setDescription(stadium.getDescription());

			List<StatusShift> statusShifts = statusShiftRepository.getAllStatusShiftByDate(date);
			List<StatusShiftResponse> statusShiftResponses = new ArrayList<>();

			statusShifts.forEach(statusShift -> {
				if (statusShift.getShift().getStadium().getId() == stadium.getId()) {

					StatusShiftResponse statusShiftResponse = new StatusShiftResponse();
					statusShiftResponse.setId(statusShift.getId());

					ShiftDTO shiftDTO = new ShiftDTO();
					shiftDTO.setId(statusShift.getShift().getId());
					shiftDTO.setName(statusShift.getShift().getName());
					shiftDTO.setTime_start(statusShift.getShift().getTime_start());
					shiftDTO.setTime_end(statusShift.getShift().getTime_end());
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
					statusShiftResponse.setDate(statusShift.getDate());
					statusShiftResponse.setNote(statusShift.getNote());
					statusShiftResponses.add(statusShiftResponse);
				}
			});

			if (statusShiftResponses.isEmpty()) {
				stadium.getShifts().forEach(shift -> {
					StatusShiftResponse statusShiftResponse = new StatusShiftResponse();

					ShiftDTO shiftDTO = new ShiftDTO();
					shiftDTO.setId(shift.getId());
					shiftDTO.setName(shift.getName());
					shiftDTO.setTime_start(shift.getTime_start());
					shiftDTO.setTime_end(shift.getTime_end());
					shiftDTO.setCash(shift.getCash());

					statusShiftResponse.setShiftDTO(shiftDTO);
					statusShiftResponse.setStatus(0);

					statusShiftResponses.add(statusShiftResponse);

				});

			} else {
				for (int i = 0; i < stadium.getShifts().size(); i++) {
					Shift shift = stadium.getShifts().get(i);
					boolean check = true;
					for (int j = 0; j < statusShiftResponses.size(); j++) {
						if (statusShiftResponses.get(j).getShiftDTO().getId() == shift.getId()) {
							check = false;
						}
					}
					if (check) {

						StatusShiftResponse statusShiftResponse = new StatusShiftResponse();
						ShiftDTO shiftDTO = new ShiftDTO();
						shiftDTO.setId(shift.getId());
						shiftDTO.setName(shift.getName());
						shiftDTO.setTime_start(shift.getTime_start());
						shiftDTO.setTime_end(shift.getTime_end());
						shiftDTO.setCash(shift.getCash());

						statusShiftResponse.setShiftDTO(shiftDTO);
						statusShiftResponse.setStatus(0);

						statusShiftResponses.add(statusShiftResponse);
					}
				}
			}

			stadiumDTO.setStatusShiftResponses(statusShiftResponses);
			stadiumDTOs.add(stadiumDTO);
		});
		return stadiumDTOs;
	}

//	@Override
//	public List<StatusShiftResponse> getFullByIdStadiumWithStatus(int id, int status) {
//
//		List<StatusShift> statusShifts = statusShiftRepository.getFullByStatus(status);
//		List<StatusShiftResponse> statusShiftResponses = new ArrayList<>();
//		statusShifts.forEach(statusShift -> {
//			if (statusShift.getShift().getStadium().getId() == id) {
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
//				statusShiftResponse.setStatus(statusShift.getStatus());
//				statusShiftResponse.setDate(statusShift.getDate());
//				statusShiftResponse.setNote(statusShift.getNote());
//				statusShiftResponses.add(statusShiftResponse);
//			}
//		});
//
//		return statusShiftResponses;
//	}

	@Override
	public int deleteStadium(int id,int idUser) {
		if(stadiumRepository.findById(id).get().getAddress().getUser().getId()==idUser) {
			stadiumRepository.deleteById(id);
			return 1;
		}
		else return -1;
		
	}

	@Override
	public List<StatusShiftResponse> notifyConfirmForUser(int idUser,int status) {

		List<StatusShift> statusShifts = statusShiftRepository.getFullByStatus(status);
		List<StatusShiftResponse> statusShiftResponses = new ArrayList<>();
		statusShifts.forEach(statusShift -> {
			if (statusShift.getUser().getId() == idUser && statusShift.getUser_confirm()==0) {

				StatusShiftResponse statusShiftResponse = new StatusShiftResponse();
				statusShiftResponse.setId(statusShift.getId());

				ShiftDTO shiftDTO = new ShiftDTO();
				shiftDTO.setId(statusShift.getShift().getId());
				shiftDTO.setName(statusShift.getShift().getName());
				shiftDTO.setTime_start(statusShift.getShift().getTime_start());
				shiftDTO.setTime_end(statusShift.getShift().getTime_end());
				shiftDTO.setCash(statusShift.getShift().getCash());
				statusShiftResponse.setShiftDTO(shiftDTO);

				InformationUser informationUser = new InformationUser();
				informationUser.setId(statusShift.getUser().getId());
				informationUser.setFullName(statusShift.getUser().getFullName());
				informationUser.setEmail(statusShift.getUser().getEmail());
				informationUser.setPhone(statusShift.getUser().getPhone());
				informationUser.setImageURL(statusShift.getUser().getImageURL());

				statusShiftResponse.setUser(informationUser);
				
				AddressDTO addressDTO=new AddressDTO();
				new AddressServiceImpl().entity2DTO(addressDTO, statusShift.getShift().getStadium().getAddress());
				statusShiftResponse.setAddressDTO(addressDTO);
				
				StadiumDTO stadiumDTO = new StadiumDTO();
				stadiumDTO.setId(statusShift.getShift().getStadium().getId());
				stadiumDTO.setName(statusShift.getShift().getStadium().getName());
				stadiumDTO.setMaType(statusShift.getShift().getStadium().getType());
				stadiumDTO.setType(TypeStadium.getTypeByValue(statusShift.getShift().getStadium().getType()).toString());
				stadiumDTO.setDescription(statusShift.getShift().getStadium().getDescription());
				statusShiftResponse.setStadiumDTO(stadiumDTO);

				statusShiftResponse.setStatus(statusShift.getStatus());
				statusShiftResponse.setDate(statusShift.getDate());
				statusShiftResponse.setNote(statusShift.getNote());
				statusShiftResponses.add(statusShiftResponse);
			}
		});

		return statusShiftResponses;
	}
	@Override
	public List<StatusShiftResponse> confirmForManager(int idUser,int status) {

		List<StatusShift> statusShifts = statusShiftRepository.getFullByStatus(status);
		List<StatusShiftResponse> statusShiftResponses = new ArrayList<>();
		statusShifts.forEach(statusShift -> {
			if (statusShift.getShift().getStadium().getAddress().getUser().getId() == idUser) {

				StatusShiftResponse statusShiftResponse = new StatusShiftResponse();
				statusShiftResponse.setId(statusShift.getId());

				ShiftDTO shiftDTO = new ShiftDTO();
				shiftDTO.setId(statusShift.getShift().getId());
				shiftDTO.setName(statusShift.getShift().getName());
				shiftDTO.setTime_start(statusShift.getShift().getTime_start());
				shiftDTO.setTime_end(statusShift.getShift().getTime_end());
				shiftDTO.setCash(statusShift.getShift().getCash());
				statusShiftResponse.setShiftDTO(shiftDTO);

				InformationUser informationUser = new InformationUser();
				informationUser.setId(statusShift.getUser().getId());
				informationUser.setFullName(statusShift.getUser().getFullName());
				informationUser.setEmail(statusShift.getUser().getEmail());
				informationUser.setPhone(statusShift.getUser().getPhone());
				informationUser.setImageURL(statusShift.getUser().getImageURL());

				statusShiftResponse.setUser(informationUser);
				
				AddressDTO addressDTO=new AddressDTO();
				new AddressServiceImpl().entity2DTO(addressDTO, statusShift.getShift().getStadium().getAddress());
				statusShiftResponse.setAddressDTO(addressDTO);
				
				StadiumDTO stadiumDTO = new StadiumDTO();
				stadiumDTO.setId(statusShift.getShift().getStadium().getId());
				stadiumDTO.setName(statusShift.getShift().getStadium().getName());
				stadiumDTO.setMaType(statusShift.getShift().getStadium().getType());
				stadiumDTO.setType(TypeStadium.getTypeByValue(statusShift.getShift().getStadium().getType()).toString());
				stadiumDTO.setDescription(statusShift.getShift().getStadium().getDescription());
				statusShiftResponse.setStadiumDTO(stadiumDTO);

				statusShiftResponse.setStatus(statusShift.getStatus());
				statusShiftResponse.setDate(statusShift.getDate());
				statusShiftResponse.setNote(statusShift.getNote());
				statusShiftResponses.add(statusShiftResponse);
			}
		});

		return statusShiftResponses;
	}
}
