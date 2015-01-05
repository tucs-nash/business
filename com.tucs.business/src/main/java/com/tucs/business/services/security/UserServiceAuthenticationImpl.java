package com.tucs.business.services.security;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;

import com.tucs.business.dao.security.interfaces.UserAuthenticationDao;
import com.tucs.business.services.interfaces.security.UserServiceAuthentication;
import com.tucs.core.model.entity.EnUser;

public class UserServiceAuthenticationImpl implements UserServiceAuthentication {

	private UserAuthenticationDao userDao;
	
	@Override
	public EnUser getUserByLogin(String email) {		
		return userDao.getUserByLogin(email);
	}
	
	@Override
	public EnUser getUserByLoginPassword(String email, String password) {		
		return userDao.getUserByLoginPassword(email, password);
	}

	@Override
	public EnUser createUserLogin(EnUser user) {
		user.setDeleted(false);
		user.setForgotPassword(false);
		user.setCreatedDate(LocalDateTime.now());
		return userDao.save(user);
	}

	@Override
	public Boolean verifyEmail(String email) {
		return userDao.verifyEmail(email);
	}
	
	@Override
	public EnUser getUser(String id) {
		return userDao.get(id);
	}
	
	@Override
	public EnUser updateUser(EnUser user) {
		user.setUpdatedDate(LocalDateTime.now());
		return userDao.update(user);
	}
	
	public UserAuthenticationDao getUserDao() {
		return userDao;
	}

	@Autowired
	public void setUserDao(UserAuthenticationDao userDao) {
		this.userDao = userDao;
	}
	
}
