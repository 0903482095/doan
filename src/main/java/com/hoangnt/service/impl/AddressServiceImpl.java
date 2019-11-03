package com.hoangnt.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoangnt.entity.Address;
import com.hoangnt.entity.City;
import com.hoangnt.entity.District;
import com.hoangnt.entity.Shift;
import com.hoangnt.entity.Stadium;
import com.hoangnt.entity.StadiumImage;
import com.hoangnt.entity.Town;
import com.hoangnt.entity.User;
import com.hoangnt.model.AddressDTO;
import com.hoangnt.model.CityDTO;
import com.hoangnt.model.DistrictDTO;
import com.hoangnt.model.ShiftDTO;
import com.hoangnt.model.StadiumDTO;
import com.hoangnt.model.StadiumImageDTO;
import com.hoangnt.model.TownDTO;
import com.hoangnt.model.request.RequestAddress;
import com.hoangnt.repository.AddressRepository;
import com.hoangnt.repository.ShiftRepository;
import com.hoangnt.repository.StadiumRepository;
import com.hoangnt.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {
	@Autowired
	AddressRepository addressRepository;

	@Autowired
	StadiumRepository stadiumRepository;

	@Autowired
	ShiftRepository shiftRepository;

	@Autowired
	EntityManager em;

	@Override
	public int addAddress(RequestAddress requestAddress) {
		Address address = new Address();
		address.setUser(new User(requestAddress.getUser()));

		address.setSpecificAddress(requestAddress.getSpecificAddress());
		address.setDescription(requestAddress.getDescription());
		address.setCity(new City(requestAddress.getMatp()));
		address.setDistrict(new District(requestAddress.getMaqh()));
		address.setTown(new Town(requestAddress.getXaid()));

		List<Stadium> stadiums = new ArrayList<Stadium>();

		requestAddress.getStadiumDTOs().forEach(stadiumDTO -> {
			Stadium stadium = new Stadium();
			stadium.setAddress(address);
			stadium.setName(stadiumDTO.getName());
			stadium.setType(stadiumDTO.getMaType());
			stadium.setDescription(stadiumDTO.getDescription());

			List<Shift> shifts = new ArrayList<Shift>();
			requestAddress.getShiftDTOs().forEach(shiftDTO -> {
				Shift shift = new Shift();
				shift.setStadium(stadium);

				shift.setName(shiftDTO.getName());
				shift.setCash(shiftDTO.getCash());
				shift.setStatus(0);
				shifts.add(shift);

			});
			stadium.setShifts(shifts);
			stadiums.add(stadium);
		});
		address.setStadiums(stadiums);
		Address address2 = addressRepository.save(address);
		return address2.getId();
	}

	@Override
	public int updateAddress(RequestAddress requestAddress) {
		Address address = addressRepository.findById(requestAddress.getId()).get();

		address.setSpecificAddress(requestAddress.getSpecificAddress());
		address.setDescription(requestAddress.getDescription());
		address.setCity(new City(requestAddress.getMatp()));
		address.setDistrict(new District(requestAddress.getMaqh()));
		address.setTown(new Town(requestAddress.getXaid()));

		List<Stadium> stadiums = new ArrayList<Stadium>();

		requestAddress.getStadiumDTOs().forEach(stadiumDTO -> {
			Stadium stadium = stadiumRepository.findById(stadiumDTO.getId()).get();
			stadium.setName(stadiumDTO.getName());
			stadium.setType(stadiumDTO.getMaType());
			stadium.setDescription(stadiumDTO.getDescription());
			
		});
		address.setStadiums(stadiums);
		Address address2 = addressRepository.save(address);
		return address2.getId();
	}

	@Override
	public List<AddressDTO> getAll() {
		List<AddressDTO> addressDTOs = new ArrayList<AddressDTO>();
		addressRepository.findAll().forEach(address -> {
			AddressDTO addressDTO = new AddressDTO();
			addressDTOs.add(entity2DTO(addressDTO, address));
		});
		return addressDTOs;
	}
	
	@Override
	public List<AddressDTO> getAllByIdUser(int id) {
		List<AddressDTO> addressDTOs = new ArrayList<AddressDTO>();
		addressRepository.getByIdUser(id).forEach(address -> {
			AddressDTO addressDTO = new AddressDTO();
			addressDTOs.add(entity2DTO(addressDTO, address));
		});
		return addressDTOs;
	}

	AddressDTO entity2DTO(AddressDTO addressDTO, Address address) {
		addressDTO.setId(address.getId());
		addressDTO.setSpecificAddress(address.getSpecificAddress());
		addressDTO.setDescription(address.getDescription());

		CityDTO cityDTO = new CityDTO();
		cityDTO.setMatp(address.getCity().getMatp());
		cityDTO.setName(address.getCity().getName());
		cityDTO.setType(address.getCity().getType());
		addressDTO.setCity(cityDTO);

		DistrictDTO districtDTO = new DistrictDTO();
		districtDTO.setMaqh(address.getDistrict().getMaqh());
		districtDTO.setName(address.getDistrict().getName());
		districtDTO.setType(address.getDistrict().getType());
		addressDTO.setDistrict(districtDTO);

		TownDTO townDTO = new TownDTO();
		townDTO.setXaid(address.getTown().getXaid());
		townDTO.setName(address.getTown().getName());
		townDTO.setType(address.getTown().getType());
		addressDTO.setTown(townDTO);

		if (address.getStadiumImages() != null) {
			List<StadiumImageDTO> stadiumImageDTOs = new ArrayList<>();
			address.getStadiumImages().forEach(image -> {
				StadiumImageDTO stadiumImageDTO = new StadiumImageDTO();
				stadiumImageDTO.setId(image.getId());
				stadiumImageDTO.setUrlImage(image.getUrlImage());

				stadiumImageDTOs.add(stadiumImageDTO);
			});
			addressDTO.setStadiumImageDTOs(stadiumImageDTOs);
		}
		return addressDTO;
	}

	@Override
	public void deleteAddress(int id) {
		addressRepository.deleteById(id);

	}

}
