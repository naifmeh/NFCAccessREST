package com.example.test;


import java.util.ArrayList;

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

@Path("nfcaccess/")
public class MyResource {

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
	
	
	@POST
	@Path("/post/{uid}/{name}/{lname}/{rank}")
	@Produces(MediaType.TEXT_PLAIN) 
	public Response addUser(@PathParam("uid") String uid,@PathParam("name") String name,@PathParam("lname") String lname,
			@PathParam("rank") int rank) {
		
		UserData user = new UserData(uid,name,lname,rank);
		int bl = (new AccessHandler()).addUid(user);
		if(bl == 0) System.out.println("OK BL");
		if(bl == 0) return Response.status(201).entity("UID : "+uid+ "entered in database").build();
		else if(bl == -1) return Response.status(Status.CONFLICT).entity("User already in database").build();
		return Response.status(500).build();
	}
	
	@PUT
	@Path("/put")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUser(UserData user) {
		
		
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
