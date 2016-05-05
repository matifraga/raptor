package ar.edu.itba.paw.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	@Override
	public Tweet register(final String msg, final User owner) {
		Tweet t = tweetDAO.create(msg, owner);
		if(t == null) {
			//TODO handle null (invalid message)
		} else {
			hashtagService.register(t);
			mentionService.register(t);
		}
		return t;
	}
	
	@Override
	public Tweet getTweet(String tweetID) {
		if(tweetID==null)
			return null;
		Tweet t = tweetDAO.getTweet(tweetID);
		if(t == null) {
			//TODO handle null
		}
		return t;
	}
	
	@Override
	public List<Tweet> getTimeline(final String id, final int resultsPerPage, final int page) {
		List<Tweet> ans = tweetDAO.getTweetsByUserID(id, resultsPerPage, page);
		if (ans == null) {
			//TODO handle null (db error)
		}
		return ans;
	}

	@Override
	public Tweet retweet(final String tweetID, final User owner) {
		Tweet t = tweetDAO.retweet(tweetID, owner);
		if(t==null) {
			//TODO handle null
		} else {
			increaseRetweetCount(tweetID);
		}
		return t;
	}

	@Override
	public List<Tweet> getMentions(final String id, final int resultsPerPage, final int page) {
		List<Tweet> ans = tweetDAO.getTweetsByMention(id, resultsPerPage, page);
		if (ans == null) {
			//TODO handle null (db error)
		}
		return ans;
	}

	@Override
	public List<Tweet> getFavourites(final String id, final int resultsPerPage, final int page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tweet> getHashtag(final String hashtag, final int resultsPerPage, final int page) {
		List<Tweet> ans = tweetDAO.getTweetsByHashtag(hashtag, resultsPerPage, page);
		if (ans == null) {
			//TODO handle null (db error)
		}
		return ans;
	}
	
	@Override
	public List<Tweet> searchTweets(final String text, final int resultsPerPage, final int page) {
		List<Tweet> ans = tweetDAO.searchTweets(text, resultsPerPage, page);
		if (ans == null) {
			//TODO handle null
		}
		return ans;
	}

	public List<Tweet> globalFeed(int resultsPerPage, int page) {
		List<Tweet> ans = tweetDAO.getGlobalFeed(resultsPerPage, page);
		if(ans == null) {
			// TODO handle null
		}
		return ans;
	}
	@Override
	public List<Tweet> currentSessionFeed(final String userID, final int resultsPerPage, final int page) {
		List<Tweet> ans = tweetDAO.getLogedInFeed(userID, resultsPerPage, page);
		if(ans == null) {
			// TODO handle null
		}
		return ans;
	}
	
	@Override
	public Integer countTweets(final User user) {
		Integer ans = tweetDAO.countTweets(user.getId());
		if(ans == null){
			//TODO handle DB error
		}
		return ans;
	}
	
	@Override
	public void increaseFavoriteCount(final String tweetID) {
		tweetDAO.increaseFavoriteCount(tweetID);	
	}
	
	@Override
	public void decreaseFavoriteCount(final String tweetID) {
		tweetDAO.decreaseFavoriteCount(tweetID);	
	}
	
	@Override
	public void increaseRetweetCount(final String tweetID) {
		tweetDAO.increaseRetweetCount(tweetID);
	}
	
	@Override
	public void decreaseRetweetCount(final String tweetID) {
		tweetDAO.decreaseRetweetCount(tweetID);
	}
	
	@Override
	public Boolean isRetweeted(final String tweetID, final User user) {
		Boolean ans = tweetDAO.isRetweeted(tweetID, user.getId());
		if(ans == null){
			//TODO handle DB error
		}
		return ans;
	}
	
	@Override
	public void unretweet(final String tweetID, final User user) {
		tweetDAO.unretweet(tweetID, user.getId());
		decreaseRetweetCount(tweetID);		
	}
}
