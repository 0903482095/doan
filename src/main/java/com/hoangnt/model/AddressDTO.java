package com.hoangnt.model;

import java.util.List;

public class AddressDTO {
	Integer id;
	String specificAddress;
	CityDTO city;
	DistrictDTO district;
	TownDTO town;
	List<StadiumDTO> stadiumDTOs;
	InformationUser user;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CityDTO getCity() {
		return city;
	}

	public void setCity(CityDTO city) {
		this.city = city;
	}

	public DistrictDTO getDistrict() {
		return district;
	}

	public void setDistrict(DistrictDTO district) {
		this.district = district;
	}

	public TownDTO getTown() {
		return town;
	}

	public void setTown(TownDTO town) {
		this.town = town;
	}

	public InformationUser getUser() {
		return user;
	}

	public void setUser(InformationUser user) {
		this.user = user;
	}

	public String getSpecificAddress() {
		return specificAddress;
	}

	public void setSpecificAddress(String specificAddress) {
		this.specificAddress = specificAddress;
	}
	public List<StadiumDTO> getStadiumDTOs() {
		return stadiumDTOs;
	}

	public void setStadiumDTOs(List<StadiumDTO> stadiumDTOs) {
		this.stadiumDTOs = stadiumDTOs;
	}

}
