package ar.edu.itba.paw.persistence;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.models.Tweet;

@Repository
public class HashtagHibernateDAO implements HashtagDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void create(final String hashtag, final Tweet tweet) {
		if (hashtag.length() >= 256) {
            return;
        }
		em.createNativeQuery("insert into hashtags (tweetid, hashtag) values(?, ?)")
			.setParameter(1, tweet.getId())
			.setParameter(2, hashtag)
			.executeUpdate();      
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getTrendingTopics(final int count) {
		return em.createNativeQuery("select h.hashtag from (SELECT UPPER(hashtag), MAX(hashtag) as hashtag, COUNT (hashtag) as hCount, MAX(timestamp) as maxTime FROM hashtags, tweets WHERE tweets.tweetID = hashtags.tweetID AND age(?,timestamp) <= '1 hours' GROUP BY UPPER(hashtag) ORDER BY hCount DESC, maxTime DESC) as h")
			.setParameter(1, new Timestamp(new Date().getTime()))
			.setMaxResults(count)
			.getResultList();
		}

}
