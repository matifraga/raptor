package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.FollowerService;
import ar.edu.itba.paw.services.TweetService;
import ar.edu.itba.paw.webapp.dto.ProfilePicturesDTO;
import ar.edu.itba.paw.webapp.dto.UserCountsDTO;
import ar.edu.itba.paw.webapp.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.core.GenericEntity;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;
import java.util.stream.Collectors;

public class UserDTOBuilder {
    private final Logger LOGGER = LoggerFactory.getLogger(UserDTOBuilder.class);
    private static final int SMALL = 50;
    private static final int MEDIUM = 150;
    private static final int LARGE = 200;
    private static final String GRAVATAR_END_POINT = "https://www.gravatar.com/avatar/";
    private static final String DEFAULT_PICTURE = "d=http%3A%2F%2Fi.imgur.com%2FgOGINGL.png";

    @Autowired
    private FollowerService followerService;
    @Autowired
    private TweetService tweetService;


    public UserDTO buildSimpleUser(User user) {
        LOGGER.debug("building : " + user.getUsername());
        String small = generateProfilePictureUrl(user, SMALL);
        String medium = generateProfilePictureUrl(user, MEDIUM);
        String large = generateProfilePictureUrl(user, LARGE);
        ProfilePicturesDTO profilePictures = new ProfilePicturesDTO(small, medium, large);
        return new UserDTO(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(),
                profilePictures, user.getVerified(), null, null);
    }

    public UserDTO buildFullUser(User user, User viewer) {
        LOGGER.debug("building : " + user.getUsername());
        String small = generateProfilePictureUrl(user, SMALL);
        String medium = generateProfilePictureUrl(user, MEDIUM);
        String large = generateProfilePictureUrl(user, LARGE);
        int rawrs = tweetService.countTweets(user);
        int followers = followerService.countFollowers(user);
        int following = followerService.countFollowing(user);
        ProfilePicturesDTO profilePictures = new ProfilePicturesDTO(small, medium, large);
        UserCountsDTO counts = new UserCountsDTO(rawrs, followers, following);
        Boolean userFollows = null;
        if (viewer != null && ! user.equals(viewer))
            userFollows = followerService.isFollower(viewer, user);
        return new UserDTO(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(),
                profilePictures, user.getVerified(), counts, userFollows);
    }
    
    public GenericEntity<List<UserDTO>> buildList(List<User> userList) {
        return new GenericEntity<List<UserDTO>>(
                userList.stream().map(this::buildSimpleUser).collect(Collectors.toList())
        ) {};
    }

    private static String md5(String s) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(StandardCharsets.UTF_8.encode(s));
            return String.format("%032x", new BigInteger(1, md5.digest()));
        } catch (Exception e) {}
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
