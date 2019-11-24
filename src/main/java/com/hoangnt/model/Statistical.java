package com.hoangnt.model;

public class Statistical<T> {
	String nameAddess;
	T value;
	public String getNameAddess() {
		return nameAddess;
	}
	public void setNameAddess(String nameAddess) {
		this.nameAddess = nameAddess;
	}
	public T getValue() {
		return value;
	}
	public void setValue(T value) {
		this.value = value;
	}
	
}
