package com.hoangnt.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "shift")
public class Shift {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	int name;
	String time_start;
	String time_end;
	Float cash;		// tien 1 ca

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stadium_id")
	Stadium stadium;
	
	@OneToMany(mappedBy = "shift",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	List<StatusShift> statusShifts;

	public Shift() {
	}

	public Shift(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
	}

	public Stadium getStadium() {
		return stadium;
	}

	public void setStadium(Stadium stadium) {
		this.stadium = stadium;
	}

	public Float getCash() {
		return cash;
	}

	public void setCash(Float cash) {
		this.cash = cash;
	}

	public List<StatusShift> getStatusShifts() {
		return statusShifts;
	}

	public void setStatusShifts(List<StatusShift> statusShifts) {
		this.statusShifts = statusShifts;
	}

	public String getTime_start() {
		return time_start;
	}

	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}

	public String getTime_end() {
		return time_end;
	}

	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}


}
