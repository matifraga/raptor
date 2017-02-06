package ar.edu.itba.paw.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.models.User;

@Repository
public class UserHibernateDAO implements UserDAO{

	@PersistenceContext
	private EntityManager em;

	@Override
	public User create(final String username, final String password, final String email, final String firstName, final String lastName) {
		if(isUsernameAvailable(username) && isEmailAvailable(email)){
			String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
			final User u = new User(username, email, firstName, lastName, hashed, false);
			em.persist(u);
			return u;
		} else 
			return null;
	}

	@Override
	public User getByUsername(final String username) {
		List<User> list  = em.createQuery("from User as u where u.username = :username", User.class)
				.setParameter("username", username)
				.getResultList();
		return list.isEmpty()? null : list.get(0);
	}

	@Override
	public List<User> searchUsers(final String text, final int resultsPerPage, final int page) {
		List<User> list = em.createQuery("from User as u WHERE UPPER(u.username) LIKE :searchText", User.class)
				.setParameter("searchText", '%' + text.toUpperCase() + '%')
				.setFirstResult((page-1)*resultsPerPage)
				.setMaxResults(resultsPerPage)
				.getResultList();
		return list;
	}

	@Override
	public Boolean isUsernameAvailable(final String username) {
		return getByUsername(username)==null;
	}
	
	@Override
	public Boolean isEmailAvailable(final String email) {
		List<User> list  = em.createQuery("from User as u where u.email = :email", User.class)
				.setParameter("email", email)
				.getResultList();
		User u = list.isEmpty()? null : list.get(0);
		return u==null;
	}
	
	@Override
	public User authenticateUser(final String username, final String password) {
		List<User> list = em.createQuery("from User as u where u.username = :username", User.class)
				.setParameter("username", username)
				.getResultList();
		
		return list.isEmpty() ? null : BCrypt.checkpw(password, list.get(0).getHash()) ? list.get(0) : null;
	}
	
	@Override
	public List<User> getFollowers(final User user, final int resultsPerPage, final int page) {
			@SuppressWarnings("unchecked")
			List<String> followerIDs = em.createNativeQuery("select followerID from followers where followingID = ?")
					.setParameter(1, user.getId())
					.setFirstResult((page-1)*resultsPerPage)
					.setMaxResults(resultsPerPage)
					.getResultList();

			if (followerIDs.isEmpty())
				return new ArrayList<>();

			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<User> cq = cb.createQuery(User.class);
			Root<User> root = cq.from(User.class);
			cq.where(root.get("id").in(followerIDs));

			return em.createQuery(cq)
					.getResultList();
	}

	@Override
	public List<User> getFollowing(final User user, final int resultsPerPage, final int page) {
			@SuppressWarnings("unchecked")
			List<String> followingIDs = em.createNativeQuery("select followingID from followers where followerID = ?")
					.setParameter(1, user.getId())
					.setFirstResult((page - 1) * resultsPerPage)
					.setMaxResults(resultsPerPage)
					.getResultList();

			if (followingIDs.isEmpty())
				return new ArrayList<>();

			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<User> cq = cb.createQuery(User.class);
			Root<User> root = cq.from(User.class);
			cq.where(root.get("id").in(followingIDs));

			return em.createQuery(cq)
					.getResultList();
	}
}

