package ar.edu.itba.paw.persistence;

public interface MentionDAO {

    /**
     * Create a mention.
     *
     * @param userID  The mentioned user's ID.
     * @param tweetID ID The tweet with the mention.
     */
    void create(final String userID, final String tweetID);
}
