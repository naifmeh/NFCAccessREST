package com.example.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

import com.example.utils.UserData;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
/**
* Class providing methods to handle different type of REST requests
*/
public class AccessHandler {
	private ArrayList<UserData> users;

	/**
	* Method returning an ArrayList of users corresponding to the {@link uid}
	* provided. The structure of the database only allow one user to be returned.
	* @param uid the uid of the user as a string. Usually contains 8 caracters.
	* @return ArrayList of Users .
	* @see UserData
	*/
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
	/**
	* Method performing a database add of the provided user.
	* @param user An UserData object that contains the user infos
	* @return an integer that takes the value of 0 if everything went fine, -1 if the user was already present,
	* or -2 if the error was server sided.
	* @see UserData
	*/
	public int addUid(UserData user) {

		try(final Connection db = (new DbConnection()).getConnection();) { //New try() structure for automatic closing

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

	/**
	* Method to modifiy the data of a specified user.
	* @param user The new values of the user to alter.
	* @return An integer that takes the value of 0 if no problem, or -1 otherwise
	* @see UserData
	*/
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

	/**
	* Method to remove a speicifed user from the database.
	* @param uid UID of the user to remove as a String
	* @return 0 if no problem, -1 otherwise
	*/
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

	public JsonObject getjsonDb() {
		JsonArray jArray;
		try(Connection db=(new DbConnection()).getConnection()) {
			String sttmt = "SELECT * FROM "+DatabaseInf.TABLE_NAME+";";
			Statement stmt = db.createStatement();
			ResultSet rs = stmt.executeQuery(sttmt);
			JsonArrayBuilder jArrayBuilder = Json.createArrayBuilder();
			while(rs.next()) {
				String uid = rs.getString(DatabaseInf.TABLE_UID);
				String name = rs.getString(DatabaseInf.TABLE_COL_NAME);
				String lname = rs.getString(DatabaseInf.TABLE_LNAME);
				int rank = rs.getInt(DatabaseInf.TABLE_RANK);
				jArrayBuilder.add(Json.createObjectBuilder()
						.add("uid", uid)
						.add("name",name)
						.add("lastName",lname)
						.add("rank",rank));
			}
			jArray = jArrayBuilder.build();
			JsonObject jObj = Json.createObjectBuilder().add("users",jArray)
					.build();
			System.out.println(jObj.toString());

			return jObj;


		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}
