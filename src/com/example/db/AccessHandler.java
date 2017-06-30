package com.example.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.example.utils.UserData;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

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
				System.out.println("USER EXISTING : ---------- "+rs.getString("uid"));
				UserData user = new UserData(rs.getString("uid"),rs.getString("uname"),rs.getString("ulastname"),rs.getInt("rank"));
				this.users.add(user);
			}
			return this.users;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return this.users;
	}
	
	public int addUid(UserData user) {
		
		try(final Connection db = (new DbConnection()).getConnection();) {
			
			PreparedStatement statement = db.prepareStatement("INSERT INTO authUsers("+DatabaseInf.TABLE_UID+","
					+DatabaseInf.TABLE_COL_NAME+","+
					DatabaseInf.TABLE_LNAME+","
					+DatabaseInf.TABLE_RANK+") VALUES ('"+user.getUid()+"','"+user.getName()+"','"+user.getLastName()+"',"+user.getRank()+");");
			
			statement.execute();
			return 0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(e instanceof MySQLIntegrityConstraintViolationException) {
				System.out.println("USER ALREADY IN DB");
				return -1; //Erreur pas mechante
				
			}
		} 
		return -2;//Autre erreur
		
	}
	
	public int alterUser(UserData user) {
		try(Connection db = (new DbConnection()).getConnection()) {
			String sttmt = "UPDATE "+DatabaseInf.TABLE_NAME+ " SET "+DatabaseInf.TABLE_UID+" = '"+user.getUid()+"', "
					+DatabaseInf.TABLE_COL_NAME+"='"+user.getName()+"', "+DatabaseInf.TABLE_LNAME+" = '"+user.getLastName()+"', "
					+DatabaseInf.TABLE_RANK+" = "+user.getRank()+" WHERE "+DatabaseInf.TABLE_UID+" = '"+user.getUid()+"';";
			
			PreparedStatement statement = db.prepareStatement(sttmt);
			statement.execute();
			return 0;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public int deleteUser(String uid) {
		try(Connection db= (new DbConnection()).getConnection()) {
			String sttmt = "DELETE FROM "+DatabaseInf.TABLE_NAME+" WHERE "+DatabaseInf.TABLE_UID+"='"+uid+"';";
			PreparedStatement statement = db.prepareStatement(sttmt);
			statement.execute();
			return 0;
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	
}
