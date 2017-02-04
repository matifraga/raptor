package ar.edu.itba.paw.persistence;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.models.Notification;
import ar.edu.itba.paw.models.NotificationType;
import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;

@Repository
public class NotificationHibernateDAO implements NotificationDAO {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Notification create(User from, User to, NotificationType type, Tweet tweet) {
		Date thisMoment = new Date();
		Notification notif = new Notification(from, to, type, tweet, thisMoment);
        em.persist(notif);
		return notif;
	}

	@Override
	public void seen(Notification notif) {
		notif.setSeen(true);
		em.merge(notif);
	}

	@Override
	public void notSeen(Notification notif) {
		notif.setSeen(false);
		em.merge(notif);
	}

	@Override
	public List<Notification> getNotifications(User user, int resultsPerPage, Date from, Date to, final int page) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Notification> cq = cb.createQuery(Notification.class);
		Root<Notification> notif = cq.from(Notification.class);
		cq.where(cb.equal(notif.get("to"), user));
		
		if(to != null)
			cq.where(cb.lessThan(notif.get("timestamp"), new Timestamp(to.getTime())));
		if(from != null)
			cq.where(cb.greaterThan(notif.get("timestamp"), new Timestamp(from.getTime())));
		
		cq.orderBy(cb.desc(notif.get("timestamp")));
		
		return em.createQuery(cq)
				.setFirstResult((page-1)*resultsPerPage)
				.setMaxResults(resultsPerPage)
				.getResultList();
	}

	@Override
	public Notification getNotificationByID(long id) {
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Notification> cq = cb.createQuery(Notification.class);
			Root<Notification> notif = cq.from(Notification.class);
			cq.where(cb.equal(notif.get("id"), id));

			return em.createQuery(cq).getSingleResult();
		}catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Integer getUnreadNotificationsCount(User user) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Notification> notif = cq.from(Notification.class);
		cq.select(cb.count(notif.get("id"))).where(cb.and(cb.equal(notif.get("to"), user),cb.equal(notif.get("seen"), true)));
		
		return em.createQuery(cq).getSingleResult().intValue();
	}
}
