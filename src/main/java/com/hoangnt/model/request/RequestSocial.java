package com.hoangnt.model.request;

public class RequestSocial {
	String providerUserId;
	String providerId;
	String displayName;
	String email;
	String imageURL;
	public String getProviderId() {
		return providerId;
	}
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getProviderUserId() {
		return providerUserId;
	}

	public void setProviderUserId(String providerUserId) {
		this.providerUserId = providerUserId;
	}

	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
}
