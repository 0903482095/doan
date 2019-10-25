package com.hoangnt.entity;

import javax.persistence.*;

@Entity
@Table(name = "account")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	@Column(name = "username", unique = true)
	String username;
	String password;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "social_id")
	SocialInfo socialInfo;
	
	@OneToOne(cascade = CascadeType.ALL,mappedBy = "account")
	User user;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public SocialInfo getSocialInfo() {
		return socialInfo;
	}

	public void setSocialInfo(SocialInfo socialInfo) {
		this.socialInfo = socialInfo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}