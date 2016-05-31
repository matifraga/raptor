package ar.edu.itba.paw.persistence;

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
		// TODO Auto-generated method stub
	}

	@Override
	public List<String> getTrendingTopics(final int count) {
		// TODO Auto-generated method stub
		return null;
	}

}
