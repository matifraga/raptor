package ar.edu.itba.paw.persistence;

import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.models.User;

@Repository
public class UserHibernate implements UserDAO {
	
	/*package*/ static final int USER_ID_LENGTH = 12;

//	private static final int USERNAME_MAX_LENGTH = 100;
//	private static final int PASSWORD_MAX_LENGTH = 100;
//	private static final int EMAIL_MAX_LENGTH = 100;
//	private static final int FIRSTNAME_MAX_LENGTH = 100;
//	private static final int LASTNAME_MAX_LENGTH = 100;

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
        final TypedQuery<User> query = em.createQuery("from User as u where u.username = :username", User.class);
        query.setParameter("username", username);
        final List<User> list = query.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

	@Override
	public List<User> searchUsers(String text, int resultsPerPage, int page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isUsernameAvailable(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User authenticateUser(String username, String password) {
		// TODO Auto-generated method stub
		return null;
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