package ar.edu.itba.paw.webapp.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TweetCountsDTO {
    private int likes;
    private int rerawrs;

    /* default */ TweetCountsDTO() {}

    public TweetCountsDTO(final int likes, final int rerawrs) {
        this.likes = likes;
        this.rerawrs = rerawrs;
    }
    
    /*
     * 
     * Getters & Setters.
     * 
     * */

    public int getLikes() {
        return likes;
    }

    public int getRerawrs() {
        return rerawrs;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setRerawrs(int rerawrs) {
        this.rerawrs = rerawrs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TweetCountsDTO)) return false;

        TweetCountsDTO that = (TweetCountsDTO) o;

        if (likes != that.likes) return false;
        return rerawrs == that.rerawrs;
    }

    @Override
    public int hashCode() {
        int result = likes;
        result = 31 * result + rerawrs;
        return result;
    }
}
