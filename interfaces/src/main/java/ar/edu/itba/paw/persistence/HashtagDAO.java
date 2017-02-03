package ar.edu.itba.paw.persistence;

import java.util.List;

import ar.edu.itba.paw.models.Tweet;

public interface HashtagDAO {

    /**
     * Create a hashtag.
     *
     * @param hashtag 	The hashtag name.
     * @param tweet 	The tweet with the hashtag.
     */
    void create(final String hashtag, final Tweet tweet);

    /**
     * Get a list of hashtags.
     *
     * @param count 	Number of results expecting.
     * @return 			The list of Trending Topics.
     */
    List<String> getTrendingTopics(final int count);

}
