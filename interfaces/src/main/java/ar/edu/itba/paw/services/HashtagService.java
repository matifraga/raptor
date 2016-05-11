package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Tweet;

import java.util.List;

public interface HashtagService {

    /**
     * Store all hashtag from a tweet.
     *
     * @param tweet The hashtag's container.
     */
    void register(final Tweet tweet);

    /**
     * Get a user's list of hashtags.
     *
     * @param count Number of results expecting.
     * @return The recovered hashtags.
     */
    List<String> getTrendingTopics(final int count);

}