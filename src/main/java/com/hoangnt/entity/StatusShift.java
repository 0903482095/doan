package com.hoangnt.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="statusshift")
public class StatusShift {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	int status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shift_id")
	Shift shift;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	User user;
	
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

	public Shift getShift() {
		return shift;
	}

	public void setShift(Shift shift) {
		this.shift = shift;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
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
