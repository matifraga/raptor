package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.NotificationType;
import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.MentionDAO;
import ar.edu.itba.paw.persistence.UserDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class MentionServiceImpl implements MentionService {

    @Autowired
    private MentionDAO mentionDAO;

    @Autowired
    private UserDAO userDAO;
    
    @Autowired
    private NotificationService notificationService;

    //test
    void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    //test
    void setMentionDAO(MentionDAO mentionDAO) {
        this.mentionDAO = mentionDAO;
    }

    @Transactional
    @Override
    public void register(Tweet tweet) {
        Set<String> mentions = tweet.getMentions();
        for (String ment : mentions) {
            User user = userDAO.getByUsername(ment);
			if (user != null) {
				mentionDAO.create(user, tweet);
				notificationService.register(tweet.getOwner(), user, NotificationType.MENTION, tweet);
			}
        }
    }
}
