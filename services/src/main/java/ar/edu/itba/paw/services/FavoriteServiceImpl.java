package ar.edu.itba.paw.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.FavoriteDAO;

@Service
public class FavoriteServiceImpl implements FavoriteService {

	@Autowired
	private FavoriteDAO favoriteDAO;

	@Autowired
	private TweetService tweetService;

	//test
	void setFavoriteDAO(FavoriteDAO favoriteDAO) {
		this.favoriteDAO = favoriteDAO;
	}

	void setTweetService(TweetService tweetService) {
		this.tweetService = tweetService;
	}

	@Transactional
	@Override
	public void favorite(final String tweetID, final User user) {
		tweetService.increaseFavoriteCount(tweetID);
		favoriteDAO.favorite(tweetID, user.getId());
	}

	@Transactional
	@Override
	public Boolean isFavorited(final String tweetID, final User user) {
		Boolean ans = favoriteDAO.isFavorited(tweetID, user.getId());
		if (ans == null) {
			//TODO handle DB error
		}
		return ans;
	}

	@Transactional
	@Override
	public void unfavorite(final String tweetID, final User user) {
		tweetService.decreaseFavoriteCount(tweetID);
		favoriteDAO.unfavorite(tweetID, user.getId());
	}

}
