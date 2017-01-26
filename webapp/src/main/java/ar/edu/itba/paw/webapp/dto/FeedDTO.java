package ar.edu.itba.paw.webapp.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by luis on 1/26/17.
 */

@XmlRootElement
public class FeedDTO {
    List<TweetDTO> rawrs;

    public FeedDTO() {
    }

    public FeedDTO(List<TweetDTO> rawrs) {
        this.rawrs = rawrs;
    }

    public List<TweetDTO> getRawrs() {
        return rawrs;
    }

    public void setRawrs(List<TweetDTO> rawrs) {
        this.rawrs = rawrs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FeedDTO)) return false;

        FeedDTO feedDTO = (FeedDTO) o;

        return rawrs != null ? rawrs.equals(feedDTO.rawrs) : feedDTO.rawrs == null;
    }

    @Override
    public int hashCode() {
        return rawrs != null ? rawrs.hashCode() : 0;
    }
}
