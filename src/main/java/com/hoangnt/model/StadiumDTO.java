package com.hoangnt.model;

import java.util.List;

public class StadiumDTO {
	int id;
	String name;
	int maType;
	String type;
	String description;
	List<ShiftDTO> shiftDTOs;
	List<StatusShiftResponse> statusShiftResponses;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ShiftDTO> getShiftDTOs() {
		return shiftDTOs;
	}

	public void setShiftDTOs(List<ShiftDTO> shiftDTOs) {
		this.shiftDTOs = shiftDTOs;
	}

	public int getMaType() {
		return maType;
	}

	public void setMaType(int maType) {
		this.maType = maType;
	}

	public List<StatusShiftResponse> getStatusShiftResponses() {
		return statusShiftResponses;
	}

	public void setStatusShiftResponses(List<StatusShiftResponse> statusShiftResponses) {
		this.statusShiftResponses = statusShiftResponses;
	}
	
}
