package com.hoangnt.utils;

public enum Status {
	chuadat(0), dadat(1), dangda(2), ketthuc(3);
	private int value;
	 
	Status(int value) {
        this.value = value;
    }

    public static Status getStatusByValue(int value) {
        for (Status s : Status.values()) {
            if (s.value == value) {
                return s;
            }
        }
        return null;
    }
	
}
