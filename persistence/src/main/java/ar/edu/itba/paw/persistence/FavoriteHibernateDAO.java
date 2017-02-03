package ar.edu.itba.paw.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;

@Repository
public class FavoriteHibernateDAO implements FavoriteDAO{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void favorite(final Tweet tweet, final User user) {
		em.createNativeQuery("INSERT INTO favorites (favoriteID, tweetID) values(?, ?)")
			.setParameter(1, user.getId())
			.setParameter(2, tweet.getId())
			.executeUpdate(); 	
	}

	@Override
	public Boolean isFavorited(final Tweet tweet, final User user) {
		Boolean ans = (Boolean) em.createNativeQuery("SELECT EXISTS( SELECT * FROM favorites WHERE tweetID = ? AND favoriteID = ?)")
				.setParameter(1, tweet.isRetweet() ? tweet.getRetweet().getId() : tweet.getId())
				.setParameter(2, user.getId())
				.getSingleResult();
		return ans;
	}

	@Override
	public void unfavorite(final Tweet tweet, final User user) {
		em.createNativeQuery("DELETE FROM favorites where favoriteID = ? and tweetID = ?")
			.setParameter(1, user.getId())
			.setParameter(2, tweet.getId())
			.executeUpdate();		
	}
}
