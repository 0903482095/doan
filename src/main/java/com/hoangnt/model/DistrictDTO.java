package com.hoangnt.model;

import java.util.List;

public class DistrictDTO {
	String maqh;
	String name;
	String type;
	List<TownDTO> townDTOs;

	public String getMaqh() {
		return maqh;
	}

	public void setMaqh(String maqh) {
		this.maqh = maqh;
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

	public List<TownDTO> getTownDTOs() {
		return townDTOs;
	}

	public void setTownDTOs(List<TownDTO> townDTOs) {
		this.townDTOs = townDTOs;
	}
}
