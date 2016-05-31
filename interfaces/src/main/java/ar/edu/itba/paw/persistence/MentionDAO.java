package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;

public interface MentionDAO {

    /**
     * Create a mention.
     *
     * @param user  The mentioned user.
     * @param tweet The tweet with the mention.
     */
    void create(final User user, final Tweet tweet);
}
