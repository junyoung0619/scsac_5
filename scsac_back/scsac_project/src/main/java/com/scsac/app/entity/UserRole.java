package com.scsac.app.entity;

public enum UserRole {
	ADMIN,
	READY,
	BEFORETEST,
	AFTERTEST;
	
    public static UserRole transform(int authorityNum, String name) {
        if (authorityNum == 1) {
        	return ADMIN;
        } else {
        	if(name==null) return READY;
        	else if (authorityNum == 2) return AFTERTEST;
        	else return BEFORETEST;        		
        }
    }
}
