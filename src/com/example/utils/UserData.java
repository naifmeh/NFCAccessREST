package com.example.utils;



public class UserData {
	
	private String uid;
	private String name;
	private String lastName;
	private int rank;
	
	public UserData(String id,String nme,String lastN,int rk) {
		super();
		this.uid = id;
		this.name = nme;
		this.lastName = lastN;
		this.rank = rk;
	}
	public UserData() {
		
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

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "User { uid :"+ this.getUid()+", name :"+this.getName()+", lastname :"+this.getLastName()+", rank = "+String.valueOf(this.getRank());
	}
	

}
