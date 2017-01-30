package ar.edu.itba.paw.webapp.viewmodels;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import ar.edu.itba.paw.models.Notification;
import ar.edu.itba.paw.models.NotificationType;

public class NotificationViewModel {

    private final UserViewModel from;
    private final UserViewModel to;
    private final NotificationType type;
    private final Date timestamp;
    private final TweetViewModel tweet;
    private final String messageCode;

    public NotificationViewModel(Notification notification) {
        this.from = UserViewModel.transformUser(notification.getFrom(), 50);
        this.to = UserViewModel.transformUser(notification.getTo(), 50);
        this.type = notification.getType();
        this.timestamp = notification.getTimestamp();
        if(notification.getTweet() != null)
         this.tweet = TweetViewModel.transformTweet(notification.getTweet(), false,false);
        else this.tweet = null;

        switch (type) {
            case MENTION:
                this.messageCode = "notifications.mention";
                break;
            case RETWEET:
                this.messageCode = "notifications.rerawr";
                break;
            case FOLLOW:
                this.messageCode = "notifications.followed";
                break;
            case UNFOLLOW:
                this.messageCode = "notifications.unfollowed";
                break;
            case FAVORITE:
                this.messageCode = "notifications.liked";
                break;
            default:
                this.messageCode = null;
                break;
        }

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

    public Date getTimestamp() {
        return timestamp;
    }

    public TweetViewModel getTweet() {
        return tweet;
    }

    public String getMessageCode() {
        return messageCode;
    }
}
