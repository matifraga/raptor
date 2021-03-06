package ar.edu.itba.paw.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.paw.models.Notification;
import ar.edu.itba.paw.models.NotificationType;
import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.NotificationDAO;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private NotificationDAO notifDAO;
	
	@Transactional
	@Override
	public void register(final User from, final User to, final NotificationType type, final Tweet tweet) {
		notifDAO.create(from, to, type, tweet);
	}

	@Transactional
	@Override
	public void seen(final Notification notif) {
		notifDAO.seen(notif);
	}

	@Transactional
	@Override
	public void notSeen(final Notification notif) {
		notifDAO.notSeen(notif);
	}

	@Transactional
	@Override
	public List<Notification> getNotifications(final User user, int resultsPerPage, final Date from, final Date to, final int page) {
		return notifDAO.getNotifications(user, resultsPerPage, from, to, page);
	}

	@Override
	public Notification getNotificationByID(long id) {
		return notifDAO.getNotificationByID(id);
	}

	@Override
	public Integer getUnreadNotificationsCount(User user) {
		return notifDAO.getUnreadNotificationsCount(user);
	}
}
