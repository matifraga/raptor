package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Tweet;

public interface MentionService {

    /**
     * Store all mentions from a tweet.
     *
     * @param tweet 	The hashtag's container
     * @param mentioner The owner of the tweet                 .
     */
    void register(final Tweet tweet);
}
