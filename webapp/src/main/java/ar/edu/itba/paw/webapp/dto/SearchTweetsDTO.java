package ar.edu.itba.paw.webapp.dto;

public class SearchTweetsDTO extends SearchDTO<FeedDTO>{
	
	public SearchTweetsDTO() {
		type = "rawrs";
	}
}
