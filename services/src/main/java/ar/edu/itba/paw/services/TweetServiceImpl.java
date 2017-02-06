package ar.edu.itba.paw.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.paw.models.NotificationType;
import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.TweetDAO;

@Service
public class TweetServiceImpl implements TweetService {

    @Autowired
    private TweetDAO tweetDAO;

    @Autowired
    private HashtagService hashtagService;

    @Autowired
    private MentionService mentionService;
    
    @Autowired
    private NotificationService notificationService;

    //test
    void setTweetDAO(TweetDAO tweetDAO) {
        this.tweetDAO = tweetDAO;
    }

    //test
    void setHashtagService(HashtagService hs) {
        this.hashtagService = hs;
    }

    //test
    void setMentionService(MentionService ms) {
        this.mentionService = ms;
    }

	@Transactional
    @Override
    public Tweet register(final String msg, final User owner) {
        Tweet t = tweetDAO.create(msg, owner);
        if (t == null) {
            //TODO handle null (invalid message)
        } else {
            hashtagService.register(t);
            mentionService.register(t,owner);
        }
        return t;
    }

	@Transactional
    @Override
    public Tweet getTweet(final String tweetID) {
        if (tweetID == null)
            return null;
        Tweet t = tweetDAO.getTweetById(tweetID);
        if (t == null) {
            //TODO handle null
        }
        return t;
    }

	@Transactional
    @Override
    public List<Tweet> getTimeline(final User user, final int resultsPerPage, final Date from, final Date to, final int page) {
        List<Tweet> ans = tweetDAO.getTweetsForUser(user, resultsPerPage, from, to, page);
        if (ans == null) {
            //TODO handle null (db error)
        }
        return ans;
    }

	@Transactional
    @Override
    public Tweet retweet(final Tweet tweet, final User owner) {
        Tweet t = tweetDAO.retweet(tweet, owner);
        if (!owner.equals(tweet.getOwner()))
            notificationService.register(owner, tweet.getOwner(), NotificationType.RETWEET, tweet);
        if (t == null) {
            //TODO handle null
        } else {
            increaseRetweetCount(tweet);
        }
        return t;
    }

	@Transactional
    @Override
    public List<Tweet> getMentions(final User user, final int resultsPerPage, final Date from, final Date to, final int page) {
        List<Tweet> ans = tweetDAO.getTweetsByMention(user, resultsPerPage, from, to, page);
        if (ans == null) {
            //TODO handle null (db error)
        }
        return ans;
    }

	@Transactional
    @Override
    public List<Tweet> getFavorites(final User user, final int resultsPerPage, final Date from, final Date to, final int page) {
        List<Tweet> ans = tweetDAO.getFavorites(user, resultsPerPage, from, to, page);
        if (ans == null) {
            //TODO handle null (db error)
        }
        return ans;
    }

	@Transactional
    @Override
    public List<Tweet> getHashtag(final String hashtag, final int resultsPerPage, final Date from, final Date to, final int page) {
        List<Tweet> ans = tweetDAO.getTweetsByHashtag(hashtag, resultsPerPage, from, to, page);
        if (ans == null) {
            //TODO handle null (db error)
        }
        return ans;
    }

	@Transactional
    @Override
	public List<Tweet> searchTweets(final String text, final int resultsPerPage, final Date from, final Date to, final int page) {
        List<Tweet> ans = tweetDAO.searchTweets(text, resultsPerPage, from, to, page);
        if (ans == null) {
            //TODO handle null
        }
        return ans;
    }

	@Transactional
	@Override 
    public List<Tweet> globalFeed(int resultsPerPage, final Date from, final Date to, final int page) {
        List<Tweet> ans = tweetDAO.getGlobalFeed(resultsPerPage, from, to, page);
        if (ans == null) {
            // TODO handle null
        }
        return ans;
    }

	@Transactional
    @Override
    public List<Tweet> currentSessionFeed(final User user, final int resultsPerPage, final Date from, final Date to, final int page) {
        List<Tweet> ans = tweetDAO.getLogedInFeed(user, resultsPerPage, from, to, page);
        if (ans == null) {
            // TODO handle null
        }
        return ans;
    }

	@Transactional
    @Override
    public Integer countTweets(final User user) {
        Integer ans = tweetDAO.countTweets(user);
        if (ans == null) {
            //TODO handle DB error
        }
        return ans;
    }

	@Transactional
    @Override
    public void increaseFavoriteCount(final Tweet tweet) {
        tweetDAO.increaseFavoriteCount(tweet);
    }

	@Transactional
    @Override
    public void decreaseFavoriteCount(final Tweet tweet) {
        tweetDAO.decreaseFavoriteCount(tweet);
    }

	@Transactional
    @Override
    public void increaseRetweetCount(final Tweet tweet) {
        tweetDAO.increaseRetweetCount(tweet);
    }

	@Transactional
    @Override
    public void decreaseRetweetCount(final Tweet tweet) {
        tweetDAO.decreaseRetweetCount(tweet);
    }
	
	@Transactional
    @Override
    public Boolean isRetweeted(final Tweet tweet, final User user) {
        Boolean ans = tweetDAO.isRetweeted(tweet, user);
        if (ans == null) {
            //TODO handle DB error
        }
        return ans;
    }

	@Transactional
    @Override
    public void unretweet(final Tweet tweet, final User user) {
        tweetDAO.unretweet(tweet, user);
        decreaseRetweetCount(tweet);
    }
}
