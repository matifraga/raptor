package ar.edu.itba.paw.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;

@Repository
public class MentionHibernateDAO implements MentionDAO {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void create(final User user, final Tweet tweet) {
		em.createNativeQuery("INSERT INTO mentions (userID, tweetID) values(?, ?)")
			.setParameter(1, user.getId())
			.setParameter(2, tweet.getId())
			.executeUpdate();     //TODO check if execute update returns >0 to see if deletion was ok
	}

}
