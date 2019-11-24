package com.hoangnt.model;

import java.util.List;

public class StatisticalAll<T> {
	List<Statistical<T>> statisticals;
	String date;
	T total;
	public List<Statistical<T>> getStatisticals() {
		return statisticals;
	}
	public void setStatisticals(List<Statistical<T>> statisticals) {
		this.statisticals = statisticals;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public T getTotal() {
		return total;
	}
	public void setTotal(T total) {
		this.total = total;
	}
	
	
}
