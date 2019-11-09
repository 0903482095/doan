package com.hoangnt.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "address")
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	@Column(name = "specific_address")
	String specificAddress;
	
	String description;
	@OneToOne()
	@JoinColumn(name = "matp")
	private City city;

	@OneToOne()
	@JoinColumn(name = "maqh")
	private District district;

	@OneToOne()
	@JoinColumn(name = "xaid")
	private Town town;
	
	Double latitude;
	Double longitude;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "address", fetch = FetchType.LAZY)
	List<Stadium> stadiums;
	
	@OneToMany(mappedBy = "stadiumImage",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	List<StadiumImage> stadiumImages;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	User user;

	public Address() {
		super();
	}

	public Address(int id) {
		super();
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	public Town getTown() {
		return town;
	}

	public void setTown(Town town) {
		this.town = town;
	}

	public List<Stadium> getStadiums() {
		return stadiums;
	}

	public void setStadiums(List<Stadium> stadiums) {
		this.stadiums = stadiums;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getSpecificAddress() {
		return specificAddress;
	}

	public void setSpecificAddress(String specificAddress) {
		this.specificAddress = specificAddress;
	}

	public List<StadiumImage> getStadiumImages() {
		return stadiumImages;
	}

	public void setStadiumImages(List<StadiumImage> stadiumImages) {
		this.stadiumImages = stadiumImages;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	
}
