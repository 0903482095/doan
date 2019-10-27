package com.hoangnt.model;

import java.util.List;

public class AddressDTO {
	Integer id;
	String specificAddress;
	String description;
	CityDTO city;
	DistrictDTO district;
	TownDTO town;
	List<StadiumDTO> stadiumDTOs;
	List<StadiumImageDTO> stadiumImageDTOs;
	
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

	public List<StadiumImageDTO> getStadiumImageDTOs() {
		return stadiumImageDTOs;
	}

	public void setStadiumImageDTOs(List<StadiumImageDTO> stadiumImageDTOs) {
		this.stadiumImageDTOs = stadiumImageDTOs;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
