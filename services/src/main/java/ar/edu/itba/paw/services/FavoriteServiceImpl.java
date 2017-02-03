package ar.edu.itba.paw.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.paw.models.NotificationType;
import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.FavoriteDAO;

@Service
public class FavoriteServiceImpl implements FavoriteService {

	@Autowired
	private FavoriteDAO favoriteDAO;

	@Autowired
	private TweetService tweetService;
	
	@Autowired
	private NotificationService notificationService;

	//test
	void setFavoriteDAO(FavoriteDAO favoriteDAO) {
		this.favoriteDAO = favoriteDAO;
	}

	void setTweetService(TweetService tweetService) {
		this.tweetService = tweetService;
	}

	@Transactional
	@Override
	public void favorite(final Tweet tweet, final User user) {
		tweetService.increaseFavoriteCount(tweet);
		favoriteDAO.favorite(tweet, user);
		notificationService.register(user, tweet.getOwner(), NotificationType.FAVORITE, tweet);
	}

	@Transactional
	@Override
	public Boolean isFavorited(final Tweet tweet, final User user) {
		Boolean ans = favoriteDAO.isFavorited(tweet, user);
		if (ans == null) {
			//TODO handle DB error
		}
		return ans;
	}

	@Transactional
	@Override
	public void unfavorite(final Tweet tweet, final User user) {
		tweetService.decreaseFavoriteCount(tweet);
		favoriteDAO.unfavorite(tweet, user);
	}
}
