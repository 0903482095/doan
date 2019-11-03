package com.hoangnt.utils;

public enum TypeStadium {
	san7(0), san9(1), san11(2);
	private int value;
	 
	TypeStadium(int value) {
        this.value = value;
    }

    public static TypeStadium getTypeByValue(int value) {
        for (TypeStadium s : TypeStadium.values()) {
            if (s.value == value) {
                return s;
            }
        }
        return null;
    }
}
