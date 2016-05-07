package ar.edu.itba.paw.webapp.controllers;

import static ar.edu.itba.paw.webapp.viewmodels.TweetViewModel.transformTweet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    protected Map<String, Object> buildPageInfo(Integer page, Integer resultsPerPage, Integer resultsInPage, String pageBase) {

        Map<String, Object> pageInfo = new HashMap<String, Object>();

        Boolean hasPrevious;
        Boolean hasNext;

        if (page > 1) {
            hasPrevious = Boolean.TRUE;
            Map<String, Object> previous = new HashMap<String, Object>();
            if (page == 1) {

            }
            previous.put("link", pageBase + "?" + "page=" + (page-1));
            previous.put("titleCode", "tweetList.previousTitle");
            pageInfo.put("previous", previous);
        } else {
            hasPrevious = Boolean.FALSE;
        }

        pageInfo.put("hasPrevious", hasPrevious);

        if (resultsInPage == resultsPerPage) {
            hasNext = Boolean.TRUE;
            Map<String, Object> next = new HashMap<String, Object>();
            next.put("link", pageBase + "?" + "page=" + (page+1));
            next.put("titleCode", "tweetList.nextTitle");
            pageInfo.put("next", next);
        } else {
            hasNext = Boolean.FALSE;
        }

        pageInfo.put("hasNext", hasNext);

        return pageInfo;
    }

}
