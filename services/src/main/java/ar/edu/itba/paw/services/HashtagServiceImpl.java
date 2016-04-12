package ar.edu.itba.paw.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.persistence.HashtagDAO;

@Service
public class HashtagServiceImpl implements HashtagService {

	@Autowired
	private HashtagDAO hashtagDAO;
	
	void setHashtagDAO(HashtagDAO hs) {
		hashtagDAO = hs;
	}
	
	@Override
	public void register(final Tweet tweet) {
		Set<String> hashtags = tweet.getHashtags();
		String id = tweet.getId();
		for (String hs : hashtags) {
			hashtagDAO.create(hs, id);
		}
 	}

	@Override
	public List<String> getTrendingTopics() {
		List<String> ans = hashtagDAO.getTrendingTopics();
		if (ans == null) {
			//TODO handle null (db error)
		}
		return ans;
	}
}
