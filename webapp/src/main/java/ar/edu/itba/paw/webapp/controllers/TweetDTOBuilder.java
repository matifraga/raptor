package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.FavoriteService;
import ar.edu.itba.paw.services.TweetService;
import ar.edu.itba.paw.webapp.dto.FeedDTO;
import ar.edu.itba.paw.webapp.dto.TweetCountsDTO;
import ar.edu.itba.paw.webapp.dto.TweetDTO;
import ar.edu.itba.paw.webapp.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by luis on 1/25/17.
 */
public class TweetDTOBuilder {

    @Autowired
    private UserDTOBuilder userDTOBuilder;

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private TweetService tweetService;

    public TweetDTO build (Tweet tweet, User user)  {
        System.out.println(tweet);
        TweetCountsDTO count = new TweetCountsDTO(tweet.getCountFavorites(), tweet.getCountRetweets());
        UserDTO owner = userDTOBuilder.build(tweet.getOwner());
        boolean userHasLiked = false;
        if (user != null)
            favoriteService.isFavorited(tweet, user);

        boolean userHasRerawred = false;
        if (user != null)
            tweetService.isRetweeted(tweet, user);

        return new TweetDTO(tweet.getId(), tweet.getTimestamp(), tweet.getMsg(), userHasLiked, userHasRerawred, count, owner);
    }

    public FeedDTO buildList(List<Tweet> tweetList, User user) {
        return new FeedDTO(tweetList.stream().map(tweet -> this.build(tweet,user)).collect(Collectors.toList()));
    }
}
