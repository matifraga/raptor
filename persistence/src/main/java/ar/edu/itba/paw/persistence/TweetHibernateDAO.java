package ar.edu.itba.paw.persistence;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;

@Repository
public class TweetHibernateDAO implements TweetDAO{

	@PersistenceContext
	private EntityManager em;

	@Override
	public Tweet create(final String msg, final User owner) {
		Date thisMoment = new Date();
		Tweet tweet;
		try {
			tweet = new Tweet(msg, owner, thisMoment);
	        em.persist(tweet);
	        em.flush();
	        return tweet;
		} catch (Exception e) {}
		return null;
	}

	@Override
	public Tweet retweet(final Tweet tweet, final User user) {
		Date thisMoment = new Date();
		Tweet retweet = new Tweet(user, thisMoment, tweet);
        em.persist(retweet);
		return retweet;
	}

	@Override
	public List<Tweet> getTweetsForUser(final User user, final int resultsPerPage, final Date from, final Date to, final int page) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Tweet> cq = cb.createQuery(Tweet.class);
		Root<Tweet> tweet = cq.from(Tweet.class);
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(cb.equal(tweet.get("owner"), user));
		
		if(to != null)
			predicates.add(cb.lessThan(tweet.get("timestamp"), new Timestamp(to.getTime())));
		if(from != null)
			predicates.add(cb.greaterThan(tweet.get("timestamp"), new Timestamp(from.getTime())));

		cq.select(tweet).where(predicates.toArray(new Predicate[] {}));
		cq.orderBy(cb.desc(tweet.get("timestamp")));
		List<Tweet> list = em.createQuery(cq)
				.setFirstResult((page-1)*resultsPerPage)
				.setMaxResults(resultsPerPage)
				.getResultList();
		return list;
	}

	@Override
	public List<Tweet> getTweetsByHashtag(final String hashtag, final int resultsPerPage, final Date from, final Date to, final int page) {
		@SuppressWarnings("unchecked")
		List<String> hashtagIDs = em.createNativeQuery("select tweetID from hashtags where UPPER(hashtag) = ?")
				.setParameter(1, hashtag.toUpperCase())
				.getResultList();
		if(hashtagIDs.isEmpty())
			return new ArrayList<>();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Tweet> cq = cb.createQuery(Tweet.class);
		Root<Tweet> root = cq.from(Tweet.class);
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(root.get("id").in(hashtagIDs));

		if(to != null)
			predicates.add(cb.lessThan(root.get("timestamp"), new Timestamp(to.getTime())));
		if(from != null)
			predicates.add(cb.greaterThan(root.get("timestamp"), new Timestamp(from.getTime())));
		
		cq.orderBy(cb.desc(root.get("timestamp")));

		cq.select(root).where(predicates.toArray(new Predicate[] {}));
		return em.createQuery(cq)
				.setFirstResult((page-1)*resultsPerPage)
				.setMaxResults(resultsPerPage)
				.getResultList();
	}

	@Override
	public List<Tweet> getTweetsByMention(final User user, final int resultsPerPage, final Date from, final Date to, final int page) {
		@SuppressWarnings("unchecked")
		List<String> mentionIDs = em.createNativeQuery("select tweetID from mentions where userID = ?")
				.setParameter(1, user.getId())
				.getResultList();
		if(mentionIDs.isEmpty())
			return new ArrayList<>();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Tweet> cq = cb.createQuery(Tweet.class);
		Root<Tweet> root = cq.from(Tweet.class);
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(root.get("id").in(mentionIDs));
		
		if(to != null)
			predicates.add(cb.lessThan(root.get("timestamp"), new Timestamp(to.getTime())));
		if(from != null)
			predicates.add(cb.greaterThan(root.get("timestamp"), new Timestamp(from.getTime())));
		
		cq.orderBy(cb.desc(root.get("timestamp")));

		cq.select(root).where(predicates.toArray(new Predicate[] {}));
		return em.createQuery(cq)
				.setFirstResult((page-1)*resultsPerPage)
				.setMaxResults(resultsPerPage)
				.getResultList();
	}

	@Override
	public List<Tweet> searchTweets(final String text, final int resultsPerPage, final Date from, final Date to, final int page) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Tweet> cq = cb.createQuery(Tweet.class);
		Root<Tweet> root = cq.from(Tweet.class);
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(cb.like(cb.upper(root.get("msg")), '%'+text.toUpperCase()+'%'));
		
		if(to != null)
			predicates.add(cb.lessThan(root.get("timestamp"), new Timestamp(to.getTime())));
		if(from != null)
			predicates.add(cb.greaterThan(root.get("timestamp"), new Timestamp(from.getTime())));
		
		cq.orderBy(cb.desc(root.get("timestamp")));
		cq.select(root).where(predicates.toArray(new Predicate[] {}));
		return em.createQuery(cq)
				.setFirstResult((page-1)*resultsPerPage)
				.setMaxResults(resultsPerPage)
				.getResultList();
	}

	@Override
	public List<Tweet> getGlobalFeed(final int resultsPerPage, final Date from, final Date to, final int page) {
		TypedQuery<Tweet> query;
		if(from != null && to != null)
			query = em.createQuery("from Tweet as t where timestamp between :from and :to order by t.timestamp desc", Tweet.class)
				.setParameter("from", new Timestamp(from.getTime()))
				.setParameter("to", new Timestamp(to.getTime()));
		else if(from != null)
			query = em.createQuery("from Tweet as t where :from < timestamp order by t.timestamp desc", Tweet.class)
				.setParameter("from", new Timestamp(from.getTime()));
		else if(to != null)
			query = em.createQuery("from Tweet as t where :to > timestamp order by t.timestamp desc", Tweet.class)
				.setParameter("to", new Timestamp(to.getTime()));
		else
			query = em.createQuery("from Tweet as t order by t.timestamp desc", Tweet.class);
		
		return query.setFirstResult((page-1)*resultsPerPage)
					.setMaxResults(resultsPerPage)
					.getResultList();
	}

	@Override
	public List<Tweet> getLogedInFeed(final User user, final int resultsPerPage, final Date from, final Date to, final int page) {
		@SuppressWarnings("unchecked")
		List<String> followingIDs = em.createNativeQuery("select followingID from followers where followerID = :id")
				.setParameter("id", user.getId())
				.getResultList();
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Tweet> cq = cb.createQuery(Tweet.class);
		Root<Tweet> tweet = cq.from(Tweet.class);
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(followingIDs.isEmpty()?cb.equal(tweet.get("owner"),user) : cb.or(tweet.get("owner").get("id").in(followingIDs), cb.equal(tweet.get("owner"),user)));
		
		if(to != null)
			predicates.add(cb.lessThan(tweet.get("timestamp"), new Timestamp(to.getTime())));
		if(from != null)
			predicates.add(cb.greaterThan(tweet.get("timestamp"), new Timestamp(from.getTime())));
		
		cq.orderBy(cb.desc(tweet.get("timestamp")));
		cq.select(tweet).where(predicates.toArray(new Predicate[] {}));
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
		em.createQuery("UPDATE Tweet SET countFavorites = countFavorites+1 WHERE id = :id")
			.setParameter("id", tweet.getId())
			.executeUpdate();
	}

	@Override
	public void decreaseFavoriteCount(final Tweet tweet) {
		em.createQuery("UPDATE Tweet SET countFavorites = countFavorites-1 WHERE id = :id")
		.setParameter("id", tweet.getId())
		.executeUpdate();
	}

	@Override
	public void increaseRetweetCount(final Tweet tweet) {
		em.createQuery("UPDATE Tweet SET countRetweets = countRetweets+1 WHERE id = :id")
		.setParameter("id", tweet.getId())
		.executeUpdate();
	}

	@Override
	public void decreaseRetweetCount(final Tweet tweet) {
		em.createQuery("UPDATE Tweet SET countRetweets = countRetweets-1 WHERE id = :id")
		.setParameter("id", tweet.getId())
		.executeUpdate();
	}

	@Override
	public Tweet getTweetById(final String tweetID) {
		return em.createQuery("from Tweet as t where t.id = :tweetID", Tweet.class)
			.setParameter("tweetID", tweetID)
			.getSingleResult();
	}

	@Override
	public Boolean isRetweeted(final Tweet tweet, final User user) {
		Boolean ans = (Boolean) em.createNativeQuery("SELECT EXISTS( SELECT * FROM tweets WHERE retweetFrom = :isRetweet AND userID = :id)")
				.setParameter("isRetweet", tweet.isRetweet() ? tweet.getRetweet().getId() : tweet.getId())
				.setParameter("id", user.getId())
				.getSingleResult();
		return ans;
	}

	@Override
	public void unretweet(final Tweet tweet, final User user) {
		em.createNativeQuery("DELETE FROM tweets where retweetFrom = :tId and userID = :uId")
			.setParameter("tId", tweet.getId())
			.setParameter("uId", user.getId())
			.executeUpdate();
	}

	@Override
	public List<Tweet> getFavorites(final User user, final int resultsPerPage, final Date from, final Date to, final int page) {
		@SuppressWarnings("unchecked")
		List<String> favoriteTweetIDs = em.createNativeQuery("select tweetID from favorites where favoriteID = :id")
				.setParameter("id", user.getId()).getResultList();
		if(favoriteTweetIDs.isEmpty())
			return new ArrayList<>();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Tweet> cq = cb.createQuery(Tweet.class);
		Root<Tweet> tweet = cq.from(Tweet.class);
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(tweet.get("id").in(favoriteTweetIDs));
		
		if(to != null)
			predicates.add(cb.lessThan(tweet.get("timestamp"), new Timestamp(to.getTime())));
		if(from != null)
			predicates.add(cb.greaterThan(tweet.get("timestamp"), new Timestamp(from.getTime())));
		
		cq.orderBy(cb.desc(tweet.get("timestamp")));
		cq.select(tweet).where(predicates.toArray(new Predicate[] {}));
		return em.createQuery(cq)
				.setFirstResult((page-1)*resultsPerPage)
				.setMaxResults(resultsPerPage)
				.getResultList();
	}
}
