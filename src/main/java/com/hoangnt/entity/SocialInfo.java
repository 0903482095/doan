package com.hoangnt.entity;

import javax.persistence.*;

@Entity
@Table(name = "socialinfo")
public class SocialInfo {
    @Id
    @Column(name = "provider_user_id")
    String providerUserId;
    @Column(name = "provider_id")
    String providerId;
    @OneToOne(mappedBy = "socialInfo")
    Account account;

    public SocialInfo() {
        super();
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
