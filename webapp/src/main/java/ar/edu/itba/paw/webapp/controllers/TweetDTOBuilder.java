package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.FavoriteService;
import ar.edu.itba.paw.services.TweetService;
import ar.edu.itba.paw.webapp.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.core.GenericEntity;
import java.util.List;
import java.util.stream.Collectors;

public class TweetDTOBuilder {
    private final Logger LOGGER = LoggerFactory.getLogger(TweetDTOBuilder.class);
    @Autowired
    private UserDTOBuilder userDTOBuilder;

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private TweetService tweetService;

    public TweetDTO build (final Tweet tweet, final User user)  {
        LOGGER.debug("building tweet : " + tweet.getMsg());
        // el tweet original (si es un retweet se toma el que es retweeteado)
        Tweet actualTweet = tweet;
        RerawrDTO rerawr = null;
        if (tweet.isRetweet()) {
            actualTweet = tweet.getRetweet();
            UserDTO reOwner = userDTOBuilder.buildSimpleUser(tweet.getOwner());
            rerawr = new RerawrDTO(tweet.getId(),tweet.getTimestamp(),reOwner);
        }
        TweetCountsDTO count = new TweetCountsDTO(actualTweet.getCountFavorites(), actualTweet.getCountRetweets());
        UserDTO owner = userDTOBuilder.buildSimpleUser(actualTweet.getOwner());
        Boolean userHasLiked = null;
        if (user != null)
            userHasLiked = favoriteService.isFavorited(tweet, user);

        Boolean userHasRerawred = null;
        if (user != null)
            userHasRerawred = tweetService.isRetweeted(tweet, user);



        return new TweetDTO(actualTweet.getId(), actualTweet.getTimestamp(), actualTweet.getMsg(), userHasLiked, userHasRerawred, count, owner, rerawr);
    }

    public GenericEntity< List< TweetDTO > > buildList(List<Tweet> tweetList, User user) {
        return new GenericEntity<List<TweetDTO>>(
        tweetList.stream().map(tweet -> this.build(tweet,user)).collect(Collectors.toList())
        ) { };
    }
}
