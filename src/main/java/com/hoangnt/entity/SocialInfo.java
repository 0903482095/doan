package com.hoangnt.entity;

import javax.persistence.*;

@Entity
@Table(name = "socialinfo")
public class SocialInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "provider_user_id")
    String providerUserId;
    @Column(name = "provider_id")
    String providerId;
    @OneToOne(mappedBy = "socialInfo")
    Account account;

    public SocialInfo() {
        super();
    }

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

}
