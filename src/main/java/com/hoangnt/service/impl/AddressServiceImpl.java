package com.hoangnt.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.hoangnt.model.request.RequestAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoangnt.entity.Address;
import com.hoangnt.entity.City;
import com.hoangnt.entity.District;
import com.hoangnt.entity.Shift;
import com.hoangnt.entity.Stadium;
import com.hoangnt.entity.Town;
import com.hoangnt.entity.User;
import com.hoangnt.model.AddressDTO;
import com.hoangnt.model.CityDTO;
import com.hoangnt.model.DistrictDTO;
import com.hoangnt.model.TownDTO;
import com.hoangnt.repository.AddressRepository;
import com.hoangnt.service.AddressService;

@Transactional
@Service
public class AddressServiceImpl implements AddressService {
	@Autowired
	AddressRepository addressRepository;

	@Override
	public void addAddress(RequestAddress requestAddress) {
		Address address = new Address();
		address.setSpecificAddress(requestAddress.getSpecificAddress());

		address.setCity(new City(requestAddress.getMatp()));
		address.setDistrict(new District(requestAddress.getMaqh()));
		address.setTown(new Town(requestAddress.getXaid()));

		List<Stadium> stadiums = new ArrayList<Stadium>();
		requestAddress.getStadiumDTOs().forEach(stadiumDTO -> {
			Stadium stadium = new Stadium();
			stadium.setName(stadiumDTO.getName());

			List<Shift> shifts = new ArrayList<Shift>();
			stadiumDTO.getShiftDTOs().forEach(shiftDTO -> {
				Shift shift = new Shift();
				shift.setName(shiftDTO.getName());
				
				shifts.add(shift);
				shift.setStadium(stadium);
			});
			stadium.setShifts(shifts);
			stadium.setAddress(address);
			stadiums.add(stadium);
		});

		address.setStadiums(stadiums);
		address.setUser(new User(requestAddress.getUser()));
		addressRepository.save(address);

	}

}
