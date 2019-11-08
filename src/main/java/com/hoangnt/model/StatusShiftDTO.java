package com.hoangnt.model;

public class StatusShiftDTO {
	int id;
	int status;
	int shiftDTO;
	int user;
	String date;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public int getShiftDTO() {
		return shiftDTO;
	}

	public void setShiftDTO(int shiftDTO) {
		this.shiftDTO = shiftDTO;
	}

	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}

	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
}
