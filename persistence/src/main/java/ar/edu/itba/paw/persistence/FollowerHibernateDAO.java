package ar.edu.itba.paw.persistence;

import java.math.BigInteger;

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
		em.createNativeQuery("INSERT INTO followers (followerID, followingID) values(?, ?)")
				.setParameter(1, follower.getId())
				.setParameter(2, following.getId())
				.executeUpdate();		//TODO check if execute update returns >0 to see if deletion was ok
	}

	@Override
	public Boolean isFollower(final User follower, final User following) {
		return (Boolean) em.createNativeQuery("SELECT EXISTS( SELECT * FROM followers WHERE followerID = ? AND followingID = ?)")
				.setParameter(1, follower.getId())
				.setParameter(2, following.getId())
				.getSingleResult();
	}

	@Override
	public void unfollow(final User follower, final User following) {
		em.createNativeQuery("DELETE FROM followers WHERE followerID = ? and followingID = ?)")
			.setParameter(1, follower.getId())
			.setParameter(2, following.getId())
			.executeUpdate();	//TODO check if execute update returns >0 to see if deletion was ok
	}

	@Override
	public Integer countFollowers(final User user) {
		return ((BigInteger) em.createNativeQuery("select count(followerID) from (select followerID from followers where followingID = ?) as aux")
			.setParameter(1, user.getId())
			.getSingleResult()).intValue();
	}

	@Override
	public Integer countFollowing(final User user) {
		return ((BigInteger) em.createNativeQuery("select count(followingID) from (select followingID from followers where followerID = ?) as aux")
				.setParameter(1, user.getId())
				.getSingleResult()).intValue();
	}
}
