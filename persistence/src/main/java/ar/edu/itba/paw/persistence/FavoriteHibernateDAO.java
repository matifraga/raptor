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
		// TODO Auto-generated method stub	
	}

	@Override
	public Boolean isFavorited(final Tweet tweet, final User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void unfavorite(final Tweet tweet, final User user) {
		// TODO Auto-generated method stub		
	}

}
