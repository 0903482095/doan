package com.hoangnt.model;

public class StatusShiftDTO {
	int id;
	int status;
	int shiftDTO;
	int user;
	String date;
	String note;
	
	int user_confirm;
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
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getUser_confirm() {
		return user_confirm;
	}
	public void setUser_confirm(int user_confirm) {
		this.user_confirm = user_confirm;
	}
	
}
