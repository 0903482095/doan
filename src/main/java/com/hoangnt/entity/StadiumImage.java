package com.hoangnt.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "stadiumimage")
public class StadiumImage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	@Column(name = "url_image")
	String urlImage;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "address_id")
	Address stadiumImage;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Address getStadiumImage() {
		return stadiumImage;
	}

	public void setStadiumImage(Address stadiumImage) {
		this.stadiumImage = stadiumImage;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

}
