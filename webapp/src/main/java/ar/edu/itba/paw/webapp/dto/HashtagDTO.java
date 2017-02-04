package ar.edu.itba.paw.webapp.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by luis on 2/4/17.
 */
@XmlRootElement
public class HashtagDTO {
    private String hashtag;

    /*default*/ HashtagDTO() {
    }

    public HashtagDTO(String hashtag) {
        this.hashtag = hashtag;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }
}
