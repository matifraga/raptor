package ar.edu.itba.paw.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.models.User;

@Repository
public class FollowerHibernateDAO implements FollowerDAO {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void follow(final User follower, final User following) {
		Query query = em.createNativeQuery("INSERT INTO followers (followerID, followingID) values(?, ?)");
		query.setParameter(1, follower.getId()).setParameter(2, following.getId());
	}

	@Override
	public Boolean isFollower(final User follower, final User following) {
		Query query = em.createNativeQuery("SELECT EXISTS( SELECT * FROM followers WHERE followerID = ? AND followingID = ?)");
		query.setParameter(1, follower.getId()).setParameter(2, following.getId());
		return (Boolean)query.getSingleResult();
	}

	@Override
	public void unfollow(final User follower, final User following) {
		Query query = em.createNativeQuery("DELETE FROM followers WHERE followerID = ? and followingID = ?)");
		query.setParameter(1, follower.getId()).setParameter(2, following.getId());
	}

	@Override
	public Integer countFollowers(final User user) {
		
		return null;
	}

	@Override
	public Integer countFollowing(final User user) {
		// TODO Auto-generated method stub
		return null;
	}

}
