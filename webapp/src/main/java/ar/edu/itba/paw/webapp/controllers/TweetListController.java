package ar.edu.itba.paw.webapp.controllers;

import static ar.edu.itba.paw.webapp.viewmodels.TweetViewModel.transformTweet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.services.TweetService;
import ar.edu.itba.paw.webapp.viewmodels.TweetViewModel;

@Controller
public abstract class TweetListController extends RaptorController {

    @Autowired
    protected TweetService tweetService;


    public List<TweetViewModel> transform(List<Tweet> tweetList) {

        List<TweetViewModel> tweetMList = new ArrayList<>(tweetList.size());
        for (Tweet tweet : tweetList) {

            TweetViewModel tweetView;

            if (tweet.isRetweet()) {
                Tweet retweet = tweetService.getTweet(tweet.getRetweet(), (sessionUser()==null)? null : sessionUser().getId());
                tweetView = transformTweet(tweet, retweet);
            } else {
                tweetView = transformTweet(tweet);
            }

            tweetMList.add(tweetView);
        }

        return tweetMList;
    }

}
