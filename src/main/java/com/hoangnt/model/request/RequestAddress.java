package com.hoangnt.model.request;

import java.util.List;

import com.hoangnt.model.StadiumDTO;

public class RequestAddress {
    Integer id;
    String specificAddress;
    String description;
    String matp;
    String maqh;
    String xaid;
    List<StadiumDTO> stadiumDTOs;
    List<String> images;
    int user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSpecificAddress() {
        return specificAddress;
    }

    public void setSpecificAddress(String specificAddress) {
        this.specificAddress = specificAddress;
    }


    public String getMatp() {
        return matp;
    }

    public void setMatp(String matp) {
        this.matp = matp;
    }

    public String getMaqh() {
        return maqh;
    }

    public void setMaqh(String maqh) {
        this.maqh = maqh;
    }

    public String getXaid() {
        return xaid;
    }

    public void setXaid(String xaid) {
        this.xaid = xaid;
    }

    public List<StadiumDTO> getStadiumDTOs() {
        return stadiumDTOs;
    }

    public void setStadiumDTOs(List<StadiumDTO> stadiumDTOs) {
        this.stadiumDTOs = stadiumDTOs;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

	public List<String> getStadiumImageDTOs() {
		return images;
	}

	public void setStadiumImageDTOs(List<String> stadiumImageDTOs) {
		this.images = stadiumImageDTOs;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
}
