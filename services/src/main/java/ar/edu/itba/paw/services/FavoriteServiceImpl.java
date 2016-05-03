package ar.edu.itba.paw.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.FavoriteDAO;

@Service
public class FavoriteServiceImpl implements FavoriteService {

	@Autowired
	private FavoriteDAO favoriteDAO;
	
	//test
	void setFavoriteDAO(FavoriteDAO favoriteDAO) {
		this.favoriteDAO=favoriteDAO;
	}

	@Override
	public void favorite(final Tweet tweet, final User user) {
		favoriteDAO.favorite(tweet.getId(), user.getId());
	}

	@Override
	public Boolean isFavorite(final Tweet tweet, final User user) {
		Boolean ans = favoriteDAO.isFavorite(tweet.getId(), user.getId());
		if(ans == null){
			//TODO handle DB error
		}
		return ans;
	}

	@Override
	public void unfavorite(final Tweet tweet, final User user) {
		favoriteDAO.unfavorite(tweet.getId(), user.getId());	
	}

	@Override
	public Integer countFavorites(final Tweet tweet) {
		Integer ans = favoriteDAO.countFavorites(tweet.getId());
		if(ans == null){
			//TODO handle DB error
		}
		return ans;
	}
	
}
