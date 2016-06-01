package ar.edu.itba.paw.persistence;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Tweet> cq = cb.createQuery(Tweet.class);
		Root<Tweet> tweet = cq.from(Tweet.class);
		cq.where(cb.equal(tweet.get("owner"), user))
			.orderBy(cb.desc(tweet.get("timestamp")));
		List<Tweet> list = em.createQuery(/*"from Tweet as t where t.owner = :user"*/ cq)
				//.setParameter("user", user)
				.setFirstResult((page-1)*resultsPerPage)
				.setMaxResults(resultsPerPage)
				.getResultList();
		return list;
	}

	@Override
	public List<Tweet> getTweetsByHashtag(final String hashtag, final int resultsPerPage, final int page, final User sessionUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tweet> getTweetsByMention(final User user, final int resultsPerPage, final int page, final User sessionUser) {
		@SuppressWarnings("unchecked")
		List<String> mentionIDs = em.createNativeQuery("select tweetID from mentions where userID = ?")
				.setParameter(1, user.getId())
				.getResultList();
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Tweet> cq = cb.createQuery(Tweet.class);
		Root<Tweet> root = cq.from(Tweet.class);
		cq.where(root.get("id").in(mentionIDs))
			.orderBy(cb.desc(root.get("timestamp")));
	
		return em.createQuery(cq)
				.setFirstResult((page-1)*resultsPerPage)
				.setMaxResults(resultsPerPage)
				.getResultList();
	}

	@Override
	public List<Tweet> searchTweets(final String text, final int resultsPerPage, final int page, final User sessionUser) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Tweet> cq = cb.createQuery(Tweet.class);
		Root<Tweet> root = cq.from(Tweet.class);
		cq.where(cb.like(cb.upper(root.get("msg")), '%'+text+'%'))
			.orderBy(cb.desc(root.get("timestamp")));
	
		return em.createQuery(cq)
				.setFirstResult((page-1)*resultsPerPage)
				.setMaxResults(resultsPerPage)
				.getResultList();
	}

	@Override
	public List<Tweet> getGlobalFeed(final int resultsPerPage, final int page, final User sessionUser) {
		return em.createQuery("from Tweet as t order by t.timestamp desc", Tweet.class)
		.setFirstResult((page-1)*resultsPerPage)
		.setMaxResults(resultsPerPage)
		.getResultList();
	}

	@Override
	public List<Tweet> getLogedInFeed(final User user, final int resultsPerPage, final int page) {
		@SuppressWarnings("unchecked")
		List<String> followingIDs = em.createNativeQuery("select followingID from followers where followerID = ?")
				.setParameter(1, user.getId())
				.getResultList();
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Tweet> cq = cb.createQuery(Tweet.class);
		Root<Tweet> tweet = cq.from(Tweet.class);
		cq.where(cb.or(tweet.get("userID").in(followingIDs), cb.equal(tweet.get("owner"),user)))  //TODO fix first get
			.orderBy(cb.desc(tweet.get("timestamp")));
	
		return em.createQuery(cq)
				.setFirstResult((page-1)*resultsPerPage)
				.setMaxResults(resultsPerPage)
				.getResultList();
	}

	@Override
	public Integer countTweets(final User user) {
		return ((BigInteger) em.createNativeQuery("select count(aux) from (select * from tweets where tweets.userID = :id) as aux")
				.setParameter("id", user.getId())
				.getSingleResult()).intValue();
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
