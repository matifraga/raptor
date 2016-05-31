package ar.edu.itba.paw.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;

@Repository
public class FavoriteHibernateDAO implements FavoriteDAO{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void favorite(final Tweet tweet, final User user) {
		Query query = em.createNativeQuery("INSERT INTO favorites (userID, tweetID) values(?, ?)");
		query.setParameter(1, user.getId()).setParameter(2, tweet.getId());
	}

	@Override
	public Boolean isFavorited(final Tweet tweet, final User user) {
		Query query = em.createNativeQuery("SELECT EXISTS( SELECT * FROM favorites WHERE tweetID = ? AND favoriteID = ?)");
		query.setParameter(1, tweet.getId()).setParameter(2, user.getId());
		return (Boolean)query.getSingleResult();
	}

	@Override
	public void unfavorite(final Tweet tweet, final User user) {
		Query query = em.createNativeQuery("DELETE FROM TABLE followers where favoriteID = ? and tweetID = ?");
		query.setParameter(1, user.getId()).setParameter(2, tweet.getId());	
	}

}
