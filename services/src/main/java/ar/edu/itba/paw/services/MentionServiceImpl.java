package ar.edu.itba.paw.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.MentionDAO;
import ar.edu.itba.paw.persistence.UserDAO;

@Service
public class MentionServiceImpl implements MentionService {

	@Autowired
	private MentionDAO mentionDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Override
	public void register(Tweet tweet) {
		Set<String> mentions = tweet.getMentions();
		String id = tweet.getId();
		for (String ment : mentions) {
			User user = userDAO.getByUsername(ment);
			if(user != null)
				mentionDAO.create(user.getId(), id);
		}
	}

}
