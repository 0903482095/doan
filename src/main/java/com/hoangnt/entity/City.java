package com.hoangnt.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "devvn_tinhthanhpho")
public class City {
	@Id
	String matp;
	String name;
	String type;

	public City() {
		super();
	}

	public City(String matp) {
		super();
		this.matp = matp;
	}

	@OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
	List<District> districts;

	public String getMatp() {
		return matp;
	}

	public void setMatp(String matp) {
		this.matp = matp;
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

	public List<District> getDistricts() {
		return districts;
	}

	public void setDistricts(List<District> districts) {
		this.districts = districts;
	}

}
