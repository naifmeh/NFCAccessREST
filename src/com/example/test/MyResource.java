package com.example.test;


import java.util.ArrayList;

import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.example.db.AccessHandler;
import com.example.utils.UserData;

/**
* Class handling the HTTP requests. Url to access this class's methods
* is http://serverip:port/RestTest/webapi/nfcaccess/ .
*/
@Path("nfcaccess/")
public class MyResource {
	/**
	* Method handling the /get/{uid} url. Where UID refers to the user UID.
	* @param uid Provided uid in Url
	* @return Specialized HTTP response with the user rank as a PLAIN TEXT_PLAIN
	* @see Response
	*/
	@Path("/get/{uid}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public Response getAccess(@PathParam("uid") String uid)
	{
		ArrayList<UserData> users = (new AccessHandler()).getAuthUsers(uid);

		if(users.size() > 0) return Response.status(200).entity(users.get(0).getRank()).build();
		else return Response.status(403).entity(-1).build();

	}
	/**
	* Method triggered on /get/details/{uid}. Returns a JSON string containing
	* the requested user details.
	* @param uid UID of requested user .
	* @return Reponse object containing the JSON data
	* @see Response
	*/
	@Path("/get/details/{uid}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsrDetails(@PathParam("uid") String uid)
	{
		ArrayList<UserData> users = (new AccessHandler()).getAuthUsers(uid);

		if(users.size() > 0) return Response.status(Status.OK).entity(users.get(0)).build();
		else return Response.status(403).build();

	}
	/**
  * Method triggered by /get/all with no parameter. Returns the list of all users
	* in the database as a JSON array.
	* @return Response object with the JSON object if success. 5xx Http status otherwise.
	* @see Response
	*/
	@Path("/get/all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUsers() {
		JsonObject jObj = (new AccessHandler()).getjsonDb();
		if(jObj != null) return Response.status(Status.OK).entity(jObj.toString()).build();
		return Response.status(Status.EXPECTATION_FAILED).build();
	}

	/**
	* Method triggered by /post/. Headers shoudl specifiy application/json content-type
	* and body should contain JSON object with the following key-values :
	* ------------ "uid"
	* ------------ "name"
	* ------------ "lastName"
	* ------------ "rank"
	* Return value of -1 defines a conflict in the database .
	* Return value of -2 defines a server error .
	* @param user UserData object mapped from the JSON body
	* @return Response object as JSON entity with a reponse field .
	*/
	@POST
	@Path("/post/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUser(UserData user) {


		int bl = (new AccessHandler()).addUid(user);

		if(bl == 0) return Response.status(201).entity("{'reponse':0}").build();
		else if(bl == -1) return Response.status(Status.CONFLICT).entity("{'reponse':-2}").build();
		return Response.status(500).entity("{'reponse':-1}").build();
	}
	/**
	* Method triggered on /put url to modifiy existing user
	* along with a JSON body. Need to specifiy the application/json
	* content type header along with a JSON body.
	* JSON body need to have the following four keys :
	* ------------------ "uid"
	* ------------------ "name"
	* ------------------ "lastName"
	* ------------------ "rank"
	* If one of the previous field is empty, NULL will be pushed into database
	* @param user The JSON body mapped into a user.
	* @return Response with the JSON data of the modified user infos or "failed" otherwise
	*/
	@PUT
	@Path("/put")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response alterUser(UserData user) {


		AccessHandler access = new AccessHandler();
		int result = access.alterUser(user);

		switch(result) {
			case 0:
				return Response.status(Status.OK).entity(user.toString()).build();

			case -1:
				return Response.status(Status.EXPECTATION_FAILED).entity("failed").build();

		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Failed").build();
	}
	/**
	* Method triggered on /delete/{uid}. Remove an user specified by uid from the database.
	* An invalid URL will triger an EXPECTATION_FAILED HTTP response.
	* @param uid The UID specified in the url
	* @return Response object with JSON body containing uid deleted and a failed field that takes 1 of value for success,
	* -1 or -2 in case of error.
	*/
	@Path("/delete/{uid}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response delUser(@PathParam("uid") String uid) {
		AccessHandler access = new AccessHandler();
		int result = access.deleteUser(uid);
		switch(result) {
		case 0:
			return Response.status(Status.OK).entity("{'uid' :'"+uid+"', deleted : 1}").build();
		case -1:
			return Response.status(Status.EXPECTATION_FAILED).entity("{'uid' :'"+uid+"', deleted : -1}").build();
		}
		return Response.status(Status.EXPECTATION_FAILED).entity("{'uid' :'"+uid+"', deleted : -2}").build();
	}



}
