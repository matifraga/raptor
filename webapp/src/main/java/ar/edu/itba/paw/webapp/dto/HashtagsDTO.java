package ar.edu.itba.paw.webapp.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HashtagsDTO {

	private List<String> hashtags;
	
	/* default */ public HashtagsDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public HashtagsDTO(List<String> hashtags) {
        this.hashtags = hashtags;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<String> hashtags) {
        this.hashtags = hashtags;
    }
}
