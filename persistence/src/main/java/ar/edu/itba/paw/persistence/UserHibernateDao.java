package ar.edu.itba.paw.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ar.edu.itba.paw.models.User;

public class UserHibernateDao implements UserDAO{

	@PersistenceContext
	private EntityManager em;

	@Override
	public User create(final String username, final String password, final String email, final String firstName, final String lastName) {
		final User u = new User(username, email, firstName, lastName, password, false);
		em.persist(u);
		return u;
	}

	@Override
	public User getByUsername(final String username) {
		TypedQuery<User> query  = em.createQuery("from User as u where u.username = :username", User.class);
		query.setParameter("username", username);
		final List<User> list = query.getResultList();
		return list.isEmpty()? null : list.get(0);
	}

	@Override
	public List<User> searchUsers(final String text, final int resultsPerPage, final int page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isUsernameAvailable(final String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User authenticateUser(final String username, final String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getFollowers(final String userId, final int resultsPerPage, final int page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getFollowing(final String userId, final int resultsPerPage, final int page) {
		// TODO Auto-generated method stub
		return null;
	}
}
