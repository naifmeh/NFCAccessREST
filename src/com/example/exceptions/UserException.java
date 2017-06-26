package com.example.exceptions;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;



@Provider
public class UserException implements ExceptionMapper<NotFoundException>{

	@Override

	public Response toResponse(NotFoundException arg0) {
		// TODO Auto-generated method stub
	
		return Response.status(Response.Status.NOT_FOUND).type(MediaType.TEXT_PLAIN).entity("ACCESS NOT GRANTED")
				.build();
	}

}
