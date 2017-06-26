package com.example.test;


import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.db.AccessHandler;
import com.example.utils.UserData;

@Path("nfcaccess/{uid}")
public class MyResource {

	
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
	@Path("/{name}/{lname}/{rank}")
	@Produces(MediaType.TEXT_PLAIN) 
	public Response addUser(@PathParam("uid") String uid,@PathParam("name") String name,@PathParam("lname") String lname,
			@PathParam("rank") int rank) {
		
		UserData user = new UserData(uid,name,lname,rank);
		boolean bl = (new AccessHandler()).addUid(user);
		if(bl) System.out.println("OK BL");
		if(bl) return Response.status(201).entity("UID : "+uid+ "entered in database").build();
		return Response.status(500).build();
	}
	

	
}
