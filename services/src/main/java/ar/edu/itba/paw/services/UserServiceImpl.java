package ar.edu.itba.paw.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.UserDAO;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDao;

    //test
    void setUserDao(UserDAO userDao) {
        this.userDao = userDao;
    }
    
	@Transactional
    @Override
    public User register(final String username, final String password, final String email, final String firstName, final String lastName) {
        return userDao.create(username, password, email, firstName, lastName);
    }

	@Transactional
    @Override
    public List<User> searchUsers(final String text, final int resultsPerPage, final int page) {
        return userDao.searchUsers(text, resultsPerPage, page);
    }

	@Transactional
    @Override
    public User authenticateUser(final String username, final String password) {
        return userDao.authenticateUser(username, password);
    }

	@Transactional
    @Override
    public User getUserWithUsername(final String username) {
        return userDao.getByUsername(username);
    }
	
	@Transactional
    @Override
    public List<User> getFollowers(final User user, final int resultsPerPage, final int page) {
        return userDao.getFollowers(user, resultsPerPage, page);
    }

	@Transactional
    @Override
    public List<User> getFollowing(final User user, final int resultsPerPage, final int page) {
        return userDao.getFollowing(user, resultsPerPage, page);
    }
}
