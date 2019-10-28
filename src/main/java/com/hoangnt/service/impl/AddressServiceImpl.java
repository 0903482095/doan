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
		Address address;
		List<Stadium> stadiumsSS;
		if (requestAddress.getId() > 0) {
			address = addressRepository.findById(requestAddress.getId()).get();
			stadiumsSS = stadiumRepository.getByIdAddress(requestAddress.getId());
			if (requestAddress.getStadiumImageDTOs() != null) {
				List<StadiumImage> stadiumImages = new ArrayList<StadiumImage>();
				requestAddress.getStadiumImageDTOs().forEach(url -> {
					StadiumImage stadiumImage = new StadiumImage();
					stadiumImage.setUrlImage(url);
					stadiumImage.setStadiumImage(address);

					stadiumImages.add(stadiumImage);
				});
				address.setStadiumImages(stadiumImages);
				addressRepository.save(address);
				return requestAddress.getId();
			}
		} else {
			address = new Address();
			address.setUser(new User(requestAddress.getUser()));
			stadiumsSS = null;
		}

		address.setSpecificAddress(requestAddress.getSpecificAddress());
		address.setDescription(requestAddress.getDescription());
		address.setCity(new City(requestAddress.getMatp()));
		address.setDistrict(new District(requestAddress.getMaqh()));
		address.setTown(new Town(requestAddress.getXaid()));

		List<Stadium> stadiums = new ArrayList<Stadium>();

		requestAddress.getStadiumDTOs().forEach(stadiumDTO -> {
			Stadium stadium;
			if (stadiumDTO.getId() > 0) {
				stadium = stadiumRepository.findById(stadiumDTO.getId()).get();
				stadiumsSS.remove(stadium);
			} else {
				stadium = new Stadium();
				stadium.setAddress(address);
			}
			stadium.setName(stadiumDTO.getName());

			List<Shift> shifts = new ArrayList<Shift>();
			requestAddress.getShiftDTOs().forEach(shiftDTO -> {
				Shift shift = new Shift();
				shift.setStadium(stadium);

				shift.setName(shiftDTO.getName());
				shift.setTime(shiftDTO.getTime());
				shift.setCash(shiftDTO.getCash());
				shifts.add(shift);

			});
			stadium.setShifts(shifts);
			stadiums.add(stadium);
		});
		if (stadiumsSS != null) {
			stadiumsSS.forEach(stadiumS -> {
				stadiumRepository.delete(stadiumS);
			});
		}
		address.setStadiums(stadiums);
		Address address2 = addressRepository.save(address);
		if (!(requestAddress.getId() > 0)) {
			return address2.getId();
		}
		return requestAddress.getId();
	}

	@Override
	public AddressDTO getAddressById(int id) {
		Address address = addressRepository.findById(id).get();
		AddressDTO addressDTO = new AddressDTO();
		return entity2DTO(addressDTO, address);
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

		List<StadiumDTO> stadiumDTOs = new ArrayList<StadiumDTO>();
		address.getStadiums().forEach(stadium -> {
			StadiumDTO stadiumDTO = new StadiumDTO();
			stadiumDTO.setId(stadium.getId());
			stadiumDTO.setName(stadium.getName());

			List<ShiftDTO> shiftDTOs = new ArrayList<>();
			stadium.getShifts().forEach(shift -> {
				ShiftDTO shiftDTO = new ShiftDTO();
				shiftDTO.setId(shift.getId());
				shiftDTO.setName(shift.getName());
				shiftDTO.setTime(shift.getTime());
				shiftDTO.setCash(shift.getCash());

				shiftDTOs.add(shiftDTO);
			});
			stadiumDTO.setShiftDTOs(shiftDTOs);
			stadiumDTOs.add(stadiumDTO);
		});

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
		addressDTO.setStadiumDTOs(stadiumDTOs);
		return addressDTO;
	}

	@Override
	public void deleteAddress(int id) {
		// TODO Auto-generated method stub

	}

}
