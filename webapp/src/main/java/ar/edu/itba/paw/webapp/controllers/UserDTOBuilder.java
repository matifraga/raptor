package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.FollowerService;
import ar.edu.itba.paw.services.TweetService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.dto.ProfilePicturesDTO;
import ar.edu.itba.paw.webapp.dto.UserCountsDTO;
import ar.edu.itba.paw.webapp.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class UserDTOBuilder {

    private static final int SMALL = 50;
    private static final int MEDIUM = 150;
    private static final int LARGE = 200;
    private static final String GRAVATAR_END_POINT = "https://www.gravatar.com/avatar/";
    private static final String DEFAULT_PICTURE = "d=http%3A%2F%2Fi.imgur.com%2FgOGINGL.png";

    @Autowired
    private UserService userService;
    @Autowired
    private FollowerService followerService;
    @Autowired
    private TweetService tweetService;

    public UserDTO build(User user, User viewer) {
        String small = generateProfilePictureUrl(user, SMALL);
        String medium = generateProfilePictureUrl(user, MEDIUM);
        String large = generateProfilePictureUrl(user, LARGE);
        int rawrs = tweetService.countTweets(user);
        int followers = followerService.countFollowers(user);
        int following = followerService.countFollowing(user);
        boolean userFollows = false;
        if (viewer != null)
        	userFollows = followerService.isFollower(viewer, user);
        ProfilePicturesDTO profilePictures = new ProfilePicturesDTO(small, medium, large);
        UserCountsDTO counts = new UserCountsDTO(rawrs, followers, following);
        return new UserDTO(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(),
                profilePictures, user.getVerified(), counts, userFollows);
    }

    private static String md5(String s) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(StandardCharsets.UTF_8.encode(s));
            return String.format("%032x", new BigInteger(1, md5.digest()));
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    private static String generateProfilePictureUrl(User u, final int size) {
        return new StringBuilder(GRAVATAR_END_POINT)
                .append(md5(u.getEmail()))
                .append("?")
                .append(DEFAULT_PICTURE)
                .append("&s=")
                .append(size)
                .toString();
    }
}
