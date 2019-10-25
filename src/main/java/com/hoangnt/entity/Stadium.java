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
@Table(name = "stadium")
public class Stadium {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	String name;

	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "address_id")
	Address address;

	@OneToMany(mappedBy = "stadium",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	List<Shift> shifts;

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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<Shift> getShifts() {
		return shifts;
	}

	public void setShifts(List<Shift> shifts) {
		this.shifts = shifts;
	}

}
