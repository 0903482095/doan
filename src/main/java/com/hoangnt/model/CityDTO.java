package com.hoangnt.model;

import java.util.List;

public class CityDTO {
	String matp;
	String name;
	String type;
	List<DistrictDTO> districtDTOs;
	public String getMatp() {
		return matp;
	}
	public void setMatp(String matp) {
		this.matp = matp;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<DistrictDTO> getDistrictDTOs() {
		return districtDTOs;
	}
	public void setDistrictDTOs(List<DistrictDTO> districtDTOs) {
		this.districtDTOs = districtDTOs;
	}
	
}
