package com.hoangnt.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "shift")
public class Shift {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	String name;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "stadium_id")
	Stadium stadium;

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

	public Stadium getStadium() {
		return stadium;
	}

	public void setStadium(Stadium stadium) {
		this.stadium = stadium;
	}

}
