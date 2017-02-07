package ar.edu.itba.paw.webapp.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.services.UserService;

@Path("hash-every-single-user")
@Component
public class HashEverythingController {		//TODO erase it

	@Autowired
	UserService us;

	@GET
	@Path("/")
	@Produces(value = {MediaType.APPLICATION_JSON})
	public Response hashEverything(){
		us.hashEverything();
		return Response.ok().build();
	}
}
