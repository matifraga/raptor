package ar.edu.itba.paw.persistence;

import java.util.List;

public interface HashtagDAO {

    /**
     * Create a hashtag.
     *
     * @param hashtag The hashtag name.
     * @param tweetID ID The tweet with the hashtag.
     */
    void create(final String hashtag, final String tweetID);

    /**
     * Get a list of hashtags.
     *
     * @param count Number of results expecting.
     * @return The list of Trending Topics.
     */
    List<String> getTrendingTopics(final int count);

}
