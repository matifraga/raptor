package ar.edu.itba.paw.persistence;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;

/**
 * Testing model
 */
@Repository
public class TweetHibernateDAO implements TweetDAO{

	@PersistenceContext
	private EntityManager em;

	@Override
	public Tweet create(final String msg, final User owner) {
		Timestamp thisMoment = new Timestamp(new Date().getTime());
        Tweet tweet = new Tweet(msg, owner, thisMoment);
        em.persist(tweet);
		return tweet;
	}

	@Override
	public Tweet retweet(final Tweet tweet, final User user) {
		Timestamp thisMoment = new Timestamp(new Date().getTime());
        Tweet retweet = new Tweet(user, thisMoment, tweet);
        em.persist(retweet);
		return retweet;
	}

	@Override
	public List<Tweet> getTweetsForUser(final User user, final int resultsPerPage, final int page, final User sessionUser) {
		TypedQuery<Tweet> query = em.createQuery("from Tweet as t where t.owner = :user", Tweet.class);
		query.setParameter("user", user).setFirstResult((page-1)*resultsPerPage).setMaxResults(resultsPerPage);
		List<Tweet> list = query.getResultList();
		return list;
	}

	@Override
	public List<Tweet> getTweetsByHashtag(final String hashtag, final int resultsPerPage, final int page, final User sessionUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tweet> getTweetsByMention(final User user, final int resultsPerPage, final int page, final User sessionUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tweet> searchTweets(final String text, final int resultsPerPage, final int page, final User sessionUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tweet> getGlobalFeed(final int resultsPerPage, final int page, final User sessionUser) {
		return null;
	}

	@Override
	public List<Tweet> getLogedInFeed(final User user, final int resultsPerPage, final int page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer countTweets(final User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void increaseFavoriteCount(final Tweet tweet) {
		// TODO Auto-generated method stub
	}

	@Override
	public void decreaseFavoriteCount(final Tweet tweet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void increaseRetweetCount(final Tweet tweet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void decreaseRetweetCount(final Tweet tweet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Tweet getTweetById(final String tweetID, final User sessionUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isRetweeted(final Tweet tweet, final User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void unretweet(final Tweet tweet, final User user) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<Tweet> getFavorites(final User user, final int resultsPerPage, final int page, final User sessionUser) {
		// TODO Auto-generated method stub
		return null;
	}
}
