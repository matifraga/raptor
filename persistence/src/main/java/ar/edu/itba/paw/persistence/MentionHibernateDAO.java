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
		// TODO Auto-generated method stub

	}

}
