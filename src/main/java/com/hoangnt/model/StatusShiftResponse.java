package com.hoangnt.model;

public class StatusShiftResponse {
	int id;
	int status;
	ShiftDTO shiftDTO;
	InformationUser user;
	String date;
	String note;
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
	public ShiftDTO getShiftDTO() {
		return shiftDTO;
	}
	public void setShiftDTO(ShiftDTO shiftDTO) {
		this.shiftDTO = shiftDTO;
	}
	public InformationUser getUser() {
		return user;
	}
	public void setUser(InformationUser user) {
		this.user = user;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
}
