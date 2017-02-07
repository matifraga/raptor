package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.NotificationType;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.FollowerDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FollowerServiceImpl implements FollowerService {

    @Autowired
    private FollowerDAO followerDAO;
    
    @Autowired
    private NotificationService notificationService;

    //test
    void setFollowerDAO(FollowerDAO followerDAO) {
        this.followerDAO = followerDAO;
    }

	@Transactional
    @Override
    public void follow(final User follower, final User following) {
        if (follower.equals(following)) return;
        followerDAO.follow(follower, following);
        notificationService.register(follower, following, NotificationType.FOLLOW, null);
    }

	@Transactional
    @Override
    public Boolean isFollower(final User follower, final User following) {
        return followerDAO.isFollower(follower, following);
    }

	@Transactional
    @Override
    public void unfollow(final User follower, final User following) {
        if (follower.equals(following)) return;
        followerDAO.unfollow(follower, following);
        notificationService.register(follower, following, NotificationType.UNFOLLOW, null);
    }

	@Transactional
    @Override
    public Integer countFollowers(final User user) {
        return followerDAO.countFollowers(user);
    }

	@Transactional
    @Override
    public Integer countFollowing(final User user) {
        return followerDAO.countFollowing(user);
    }
}
