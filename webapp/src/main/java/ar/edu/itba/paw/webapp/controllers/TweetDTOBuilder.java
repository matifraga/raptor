package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.FavoriteService;
import ar.edu.itba.paw.services.TweetService;
import ar.edu.itba.paw.webapp.dto.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class TweetDTOBuilder {

    @Autowired
    private UserDTOBuilder userDTOBuilder;

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private TweetService tweetService;

    public TweetDTO build (final Tweet tweet, final User user)  {

        // el tweet original (si es un retweet se toma el que es retweeteado)
        Tweet actualTweet = tweet;
        RerawrDTO rerawr = null;
        if (tweet.isRetweet()) {
            actualTweet = tweet.getRetweet();
            UserDTO reOwner = userDTOBuilder.build(tweet.getOwner(),user);
            rerawr = new RerawrDTO(tweet.getId(),tweet.getTimestamp(),reOwner);
        }
        TweetCountsDTO count = new TweetCountsDTO(actualTweet.getCountFavorites(), actualTweet.getCountRetweets());
        UserDTO owner = userDTOBuilder.build(actualTweet.getOwner(),user);
        Boolean userHasLiked = null;
        if (user != null)
            userHasLiked = favoriteService.isFavorited(tweet, user);

        Boolean userHasRerawred = null;
        if (user != null)
            userHasRerawred = tweetService.isRetweeted(tweet, user);



        return new TweetDTO(actualTweet.getId(), actualTweet.getTimestamp(), actualTweet.getMsg(), userHasLiked, userHasRerawred, count, owner, rerawr);
    }

    public FeedDTO buildList(List<Tweet> tweetList, User user) {
        return new FeedDTO(tweetList.stream().map(tweet -> this.build(tweet,user)).collect(Collectors.toList()));
    }
}
