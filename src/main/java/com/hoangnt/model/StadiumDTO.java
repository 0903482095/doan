package com.hoangnt.model;

import java.util.List;

public class StadiumDTO {
	int id;
	String name;
	List<ShiftDTO> shiftDTOs;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ShiftDTO> getShiftDTOs() {
		return shiftDTOs;
	}

	public void setShiftDTOs(List<ShiftDTO> shiftDTOs) {
		this.shiftDTOs = shiftDTOs;
	}
}
