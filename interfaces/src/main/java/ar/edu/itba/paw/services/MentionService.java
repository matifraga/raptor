package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Tweet;

public interface MentionService {

    /**
     * Store all mentions from a tweet.
     *
     * @param tweet the hashtag's container.
     */
    void register(final Tweet tweet);
}
