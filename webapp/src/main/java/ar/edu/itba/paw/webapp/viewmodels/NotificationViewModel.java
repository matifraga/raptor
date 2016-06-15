package ar.edu.itba.paw.webapp.viewmodels;

import ar.edu.itba.paw.models.Notification;
import ar.edu.itba.paw.models.NotificationType;
import ar.edu.itba.paw.models.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Tomi on 6/11/16.
 */
public class NotificationViewModel {

    private final UserViewModel from;
    private final UserViewModel to;
    private final NotificationType type;
    private final Timestamp timestamp;
    private final TweetViewModel tweet;
    private final String message;

    public NotificationViewModel(Notification notification) {
        this.from = UserViewModel.transformUser(notification.getFrom(), 50);
        this.to = UserViewModel.transformUser(notification.getTo(), 50);
        this.type = notification.getType();
        this.timestamp = notification.getTimestamp();
        if(notification.getTweet() != null)
         this.tweet = TweetViewModel.transformTweet(notification.getTweet(), false,false);
        else this.tweet = null;

        StringBuilder sb = new StringBuilder();
        sb.append(notification.getFrom().getFirstName());
        sb.append(" ");

        switch (type) {
            case MENTION:
                sb.append("<strong>mentioned</strong> you.");
                break;
            case RETWEET:
                sb.append("<strong>rerawred</strong> your tweet.");
                break;
            case FOLLOW:
                sb.append("<strong>followed</strong> you.");
                break;
            case UNFOLLOW:
                sb.append("<strong>unfollowed</strong> you.");
                break;
            case FAVORITE:
                sb.append("<strong>liked</strong> your tweet.");
                break;
            default:
                break;
        }

        this.message = sb.toString();
        System.out.println(this.message);

        // xxx has tttt you.
        // xxx has tttt your tweet.

    }

    static public NotificationViewModel transformNotification(Notification notification) {
        return new NotificationViewModel(notification);
    }

    public static List<NotificationViewModel> transform(List<Notification> notificationList) {
        List<NotificationViewModel> notiMList = new ArrayList<>(notificationList.size());
        notiMList.addAll(notificationList.stream().map(noti -> transformNotification(noti)).collect(Collectors.toList()));
        return notiMList;
    }

    public UserViewModel getFrom() {
        return from;
    }

    public UserViewModel getTo() {
        return to;
    }

    public NotificationType getType() {
        return type;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public TweetViewModel getTweet() {
        return tweet;
    }

    public String getMessage() {
        return message;
    }
}
