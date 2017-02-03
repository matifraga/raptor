package ar.edu.itba.paw.webapp.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class RawrsDTO{
    private List<TweetDTO> rawrs;

    /* default */ RawrsDTO() {}

    public RawrsDTO(List<TweetDTO> rawrs) {
        this.rawrs = rawrs;
    }
    
    /*
     * 
     * Getters & Setters.
     * 
     * */

    public List<TweetDTO> getRawrs() {
        return rawrs;
    }

    public void setRawrs(List<TweetDTO> rawrs) {
        this.rawrs = rawrs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RawrsDTO)) return false;

        RawrsDTO feedDTO = (RawrsDTO) o;

        return rawrs != null ? rawrs.equals(feedDTO.rawrs) : feedDTO.rawrs == null;
    }

    @Override
    public int hashCode() {
        return rawrs != null ? rawrs.hashCode() : 0;
    }
}
