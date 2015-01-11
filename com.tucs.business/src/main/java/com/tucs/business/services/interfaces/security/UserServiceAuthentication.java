package com.tucs.business.services.interfaces.security;

import javax.transaction.Transactional;

import com.tucs.business.services.interfaces.BaseService;
import com.tucs.core.commons.dto.UserLookupsDto;
import com.tucs.core.model.entity.EnUser;

public interface UserServiceAuthentication extends BaseService{
	public EnUser getUser(String id);
	public EnUser getUserByLogin(String email);
	public EnUser getUserByLoginPassword(String email, String password);
	public Boolean verifyEmail(String email);
	
	@Transactional
	public EnUser createUserLogin(EnUser user);
	@Transactional
	public EnUser updateUser(EnUser user);
	public UserLookupsDto getUserLookups();
}
