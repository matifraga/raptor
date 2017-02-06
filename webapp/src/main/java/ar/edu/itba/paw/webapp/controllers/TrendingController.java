package ar.edu.itba.paw.webapp.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ar.edu.itba.paw.webapp.dto.HashtagDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.services.HashtagService;

@Path("trending")
@Component
public class TrendingController {
	private final Logger LOGGER = LoggerFactory.getLogger(TrendingController.class);
	@Autowired
	private HashtagService hs;
	
	@GET
	@Path("/")
	@Produces(value = {MediaType.APPLICATION_JSON})
	public Response getTrendings(@QueryParam("count") final int count){
		LOGGER.info("getTrendings");
		if(count <=0)
			return Response.status(Status.BAD_REQUEST).build();
		List<HashtagDTO> hashtags = hs.getTrendingTopics(count).stream().map(HashtagDTO::new).collect(Collectors.toList());
		LOGGER.info("hashtags: " +hashtags.size());
		GenericEntity<List<HashtagDTO>> trending = new GenericEntity<List<HashtagDTO>>(hashtags) {};
		return Response.ok(trending).build();
	}
}
