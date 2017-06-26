package com.example.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.example.utils.UserData;

public class AccessHandler {
	private ArrayList<UserData> users;
	
	public ArrayList<UserData> getAuthUsers(String uid) {
		this.users = new ArrayList<UserData>();
		
		try {
			final Connection db = (new DbConnection()).getConnection();
			PreparedStatement statement = db.prepareStatement("SELECT * FROM "+DatabaseInf.TABLE_NAME+" WHERE uid = ?");
			statement.setString(1, uid);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				System.out.println("USER ADDED : ---------- "+rs.getString("uid"));
				UserData user = new UserData(rs.getString("uid"),rs.getString("uname"),rs.getString("ulastname"),rs.getInt("rank"));
				this.users.add(user);
			}
			return this.users;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return this.users;
	}
	
	public boolean addUid(UserData user) {
		try {
			final Connection db = (new DbConnection()).getConnection();
			PreparedStatement statement = db.prepareStatement("INSERT INTO authUsers("+DatabaseInf.TABLE_UID+","
					+DatabaseInf.TABLE_COL_NAME+","+
					DatabaseInf.TABLE_LNAME+","
					+DatabaseInf.TABLE_RANK+") VALUES ('"+user.getUid()+"','"+user.getName()+"','"+user.getLastName()+"',"+user.getRank()+");");
			System.out.println("INSERT INTO authUsers("+DatabaseInf.TABLE_UID+","
					+DatabaseInf.TABLE_COL_NAME+","+
					DatabaseInf.TABLE_LNAME+","
					+DatabaseInf.TABLE_RANK+") VALUES ('"+user.getUid()+"','"+user.getName()+"','"+user.getLastName()+"',"+user.getRank()+");");
			statement.execute();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	
	
}
