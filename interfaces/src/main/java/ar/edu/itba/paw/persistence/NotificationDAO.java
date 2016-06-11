package ar.edu.itba.paw.persistence;

import java.util.List;

import ar.edu.itba.paw.models.Notification;
import ar.edu.itba.paw.models.NotificationType;
import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;

public interface NotificationDAO {
	
	/**
	 * Create a new Notification.
	 *
	 * @param from  The origin user.
	 * @param to	The destiny user.
	 * @param type	The type of notification.
	 * @param tweet The tweet of interaction.
	 */
	Notification create(final User from, final User to, final NotificationType type, final Tweet tweet);

	/**
	 * Mark a notification as SEEN.
	 *
	 * @param notif The notification.
	 */
	void seen(final Notification notif);
	
	/**
	 * Mark a notification as NOT SEEN.
	 *
	 * @param notif The notification.
	 */
	void notSeen(final Notification notif);
	
	/**
	 * Get a user's notifications.
	 * 
	 * @param user The user.
	 * @return	The list of notifications for the user.
	 */
	List<Notification> getNotifications(final User user);

}
