package com.tucs.business.services.interfaces;

import javax.transaction.Transactional;

import com.tucs.core.model.entity.EnUser;

public interface UserServiceAuthentication extends BaseService{
	public EnUser getUserByLogin(String email);
	public EnUser getUserByLoginPassword(String email, String password);
	public Boolean verifyEmail(String email);
	
	@Transactional
	public EnUser createUserLogin(EnUser user);
	@Transactional
	public EnUser updateUser(EnUser user);
}
