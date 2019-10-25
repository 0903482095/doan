package com.hoangnt.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "devvn_quanhuyen")
public class District {
	@Id
	String maqh;
	String name;
	String type;
	public District() {
		super();
	}

	public District(String maqh) {
		this.maqh = maqh;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "matp")
	City city;

	@OneToMany(mappedBy = "district", fetch = FetchType.LAZY)
	List<Town> towns;
	
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

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public List<Town> getTowns() {
		return towns;
	}

	public void setTowns(List<Town> towns) {
		this.towns = towns;
	}
	
	
}
