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
	public void follow(User follower, User following) {
		em.createNativeQuery("INSERT INTO followers (followerID, followingID) values(?, ?)")
				.setParameter(1, follower.getId())
				.setParameter(2, following.getId())
				.executeUpdate();		
	}

	@Override
	public Boolean isFollower(User follower, User following) {
		return (Boolean) em.createNativeQuery("SELECT EXISTS( SELECT * FROM followers WHERE followerID = ? AND followingID = ?)")
				.setParameter(1, follower.getId())
				.setParameter(2, following.getId())
				.getSingleResult();
	}

	@Override
	public void unfollow(User follower, User following) {
		em.createNativeQuery("DELETE FROM followers WHERE followerID = ? and followingID = ?")
			.setParameter(1, follower.getId())
			.setParameter(2, following.getId())
			.executeUpdate();	
	}

	@Override
	public Integer countFollowers( User user) {
		Integer ans = ((Number) em.createNativeQuery("select count(followerID) from followers where followingID = ?")
			.setParameter(1, user.getId())
			.getSingleResult()).intValue();
		return ans;
	}

	@Override
	public Integer countFollowing(final User user) {
		Integer ans = ((Number) em.createNativeQuery("select count(followingID) from followers where followerID = ?")
				.setParameter(1, user.getId())
				.getSingleResult()).intValue();
		return ans;
	}
}
