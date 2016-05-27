package ar.edu.itba.paw.persistence;

import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.models.User;

@Repository
public class UserHibernate implements UserDAO {
	
	/*package*/ static final int USER_ID_LENGTH = 12;

	private static final String JPA_GET_BY_USERNAME = "from User as u where u.username = :username";

	private static final String JPA_GET_USERS_CONTAINING = "from User as u where UPPER( u.username ) LIKE ('%' || :text || '%')";

	private static final String JPA_AUTHENTICATE_USER = "from User as u where u.username = :username and u.password = :password";

    @PersistenceContext
    private EntityManager em;

    @Override
    public User create(final String username, final String password,
			   final String email, final String firstName, final String lastName) {
        final User user = new User(username,email,firstName,lastName,randomUserId(),false);
        em.persist(user);
        return user;
    }

    @Override
    public User getByUsername(final String username) {
        final TypedQuery<User> query = em.createQuery(JPA_GET_BY_USERNAME, User.class);
        query.setParameter("username", username);
        final List<User> list = query.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

	@Override
	public List<User> searchUsers(final String text, final int resultsPerPage, final int page) { //TODO paging
		final TypedQuery<User> query = em.createQuery(JPA_GET_USERS_CONTAINING, User.class);
        query.setParameter("text", text);
        final List<User> list = query.getResultList();
        return list;
	}

	@Override
	public Boolean isUsernameAvailable(String username) {
		return getByUsername(username.toUpperCase()) == null;
	}

	@Override
	public User authenticateUser(String username, String password) {
		final TypedQuery<User> query = em.createQuery(JPA_AUTHENTICATE_USER, User.class);
        query.setParameter("username", username );
        query.setParameter("password", password);
        final List<User> list = query.getResultList();
        return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public List<User> getFollowers(String userId, int resultsPerPage, int page) { 
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<User> getFollowing(String userId, int resultsPerPage, int page) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Set<User> getFollowers(User user, int resultsPerPage, int page) { //TODO paging?
		return user.getFollowers();
	}
	
	public Set<User> getFollowings(User user, int resultsPerPage, int page) { //TODO paging?
		return user.getFollowings();
	}
	
	/**
	 * Sketchy method needed to be replaced
	 *
	 * @return a "random" userId
	 */

	private String randomUserId() {
		char[] characterArray = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
		char[] userId = new char[USER_ID_LENGTH];
		Random rand = new Random();

		int i = USER_ID_LENGTH - 1;
		while (i >= 0) {
			userId[i] = characterArray[rand.nextInt(characterArray.length)];
			i--;
		}

		return new String(userId);
	}

}