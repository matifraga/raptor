package ar.edu.itba.paw.webapp.controllers;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.services.HashtagService;

@Path("trending")
@Component
public class TrendingController {
	
	@Autowired
	private HashtagService hs;
	
	@GET
	@Path("/")
	@Produces(value = {MediaType.APPLICATION_JSON})
	public Response getTrendings(@QueryParam("count") final int count){
		if(count <=0)
			return Response.status(Status.BAD_REQUEST).build();
		List<String> hashtags = hs.getTrendingTopics(count);
		return Response.ok(new HashtagsDTO(hashtags)).build();
	}
}
