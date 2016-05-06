package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.FollowerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowerServiceImpl implements FollowerService {

    @Autowired
    private FollowerDAO followerDAO;

    //test
    void setFollowerDAO(FollowerDAO followerDAO) {
        this.followerDAO = followerDAO;
    }

    @Override
    public void follow(final User follower, final User following) {
        if (follower.equals(following)) return;
        followerDAO.follow(follower.getId(), following.getId());
    }

    @Override
    public Boolean isFollower(final User follower, final User following) {
        Boolean ans = followerDAO.isFollower(follower.getId(), following.getId());
        if (ans == null) {
            //TODO handle DB error
        }
        return ans;
    }

    @Override
    public void unfollow(final User follower, final User following) {
        followerDAO.unfollow(follower.getId(), following.getId());
    }

    @Override
    public Integer countFollowers(final User user) {
        Integer ans = followerDAO.countFollowers(user.getId());
        if (ans == null) {
            //TODO handle DB error
        }
        return ans;
    }

    @Override
    public Integer countFollowing(final User user) {
        Integer ans = followerDAO.countFollowing(user.getId());
        if (ans == null) {
            //TODO handle DB error
        }
        return ans;
    }
}
