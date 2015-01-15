package com.tucs.business.services.security;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;

import com.tucs.business.dao.interfaces.TyLanguageDao;
import com.tucs.business.dao.security.interfaces.UserAuthenticationDao;
import com.tucs.business.services.interfaces.security.UserServiceAuthentication;
import com.tucs.core.commons.dto.UserLookupsDto;
import com.tucs.core.commons.enums.TypeGetUser;
import com.tucs.core.model.entity.EnUser;
import com.tucs.core.model.entity.EnUser.TypeUser;

public class UserServiceAuthenticationImpl implements UserServiceAuthentication {

	private UserAuthenticationDao userDao;
	@Autowired
	private TyLanguageDao languageDao;
	
	@Override
	public EnUser getUserByLogin(String email) {		
		return userDao.getUserByLogin(email, TypeGetUser.NORMAL);
	}
	
	@Override
	public EnUser getUserBlockedByLogin(String email) {		
		return userDao.getUserByLogin(email, TypeGetUser.BLOCKED);
	}
	
	@Override
	public EnUser getUserActiveByLogin(String email) {		
		return userDao.getUserByLogin(email, TypeGetUser.ACTIVE);
	}
	
	@Override
	public EnUser getUserDeletedByLogin(String email) {		
		return userDao.getUserByLogin(email, TypeGetUser.DELETED);
	}
	
	@Override
	public EnUser getUserForgotPasswordByLogin(String email) {		
		return userDao.getUserByLogin(email, TypeGetUser.FORGOT_PASSWORD);
	}
	
	@Override
	public EnUser getUserByLoginPassword(String email, String password) {		
		return userDao.getUserByLoginPassword(email, password);
	}

	@Override
	public EnUser createUserLoginInitial(EnUser user) {
		EnUser userBlocked = getUserBlockedByLogin(user.getEmail());
		if (userBlocked != null) {
			user.setId(userBlocked.getId());
			user.setUpdatedDate(LocalDateTime.now());
		} else {			
			user.setCreatedDate(LocalDateTime.now());
		}
		
		user.setDeleted(false);
		user.setForgotPassword(false);
		return userDao.saveOrUpdate(user);
	}
	
	@Override
	public EnUser createUserBlocked(String email) {
		EnUser user = new EnUser();
		user.setEmail(email);
		user.setFirstName(" ");
		user.setLanguage(null);
		user.setLastName(" ");
		user.setPassword(email);
		user.setTypeUser(TypeUser.TYPE_USER_NORMAL);
		user.setDeleted(true);
		user.setForgotPassword(true);
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

	@Override
	public UserLookupsDto getUserLookups() {
		return new UserLookupsDto(languageDao.list());
	}
	
}
