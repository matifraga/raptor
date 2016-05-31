package ar.edu.itba.paw.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.models.User;

@Repository
public class FollowerHibernateDAO implements FollowerDAO {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void follow(final User follower, final User following) {
		// TODO Auto-generated method stub
	}

	@Override
	public Boolean isFollower(final User follower, final User following) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void unfollow(final User follower, final User following) {
		// TODO Auto-generated method stub
	}

	@Override
	public Integer countFollowers(final User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer countFollowing(final User user) {
		// TODO Auto-generated method stub
		return null;
	}

}
