package com.hoangnt.model.response;

import java.sql.Timestamp;

public class Response<T> {
	String status;
	Timestamp timestamp;
	ResponseData<T> data;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public ResponseData<T> getData() {
		return data;
	}
	public void setData(ResponseData<T> data) {
		this.data = data;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

}
