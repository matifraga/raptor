package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.persistence.HashtagDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class HashtagServiceImpl implements HashtagService {

    @Autowired
    private HashtagDAO hashtagDAO;

    //test
    void setHashtagDAO(HashtagDAO hs) {
        hashtagDAO = hs;
    }

    @Transactional
    @Override
    public void register(final Tweet tweet) {
        Set<String> hashtags = tweet.getHashtags();
        for (String hs : hashtags) {
            hashtagDAO.create(hs, tweet);
        }
    }

    @Transactional
    @Override
    public List<String> getTrendingTopics(final int count) {
        List<String> ans = hashtagDAO.getTrendingTopics(count);
        if (ans == null) {
            //TODO handle null (db error)
        }
        return ans;
    }
}
