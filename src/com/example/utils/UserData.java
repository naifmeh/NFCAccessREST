package com.example.utils;

public class UserData {
	
	private String uid;
	private String name;
	private String lastName;
	private int rank;
	
	public UserData(String id,String nme,String lastN,int rk) {
		this.uid = id;
		this.name = nme;
		this.lastName = lastN;
		this.rank = rk;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

}
