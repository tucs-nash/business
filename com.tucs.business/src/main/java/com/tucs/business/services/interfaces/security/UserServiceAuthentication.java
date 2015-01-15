package com.tucs.business.services.interfaces.security;

import javax.transaction.Transactional;

import com.tucs.business.services.interfaces.BaseService;
import com.tucs.core.commons.dto.UserLookupsDto;
import com.tucs.core.model.entity.EnUser;

public interface UserServiceAuthentication extends BaseService{
	public EnUser getUser(String id);
	public EnUser getUserByLoginPassword(String email, String password);
	public Boolean verifyEmail(String email);
	public UserLookupsDto getUserLookups();

	public EnUser getUserByLogin(String email);
	public EnUser getUserActiveByLogin(String email);
	public EnUser getUserBlockedByLogin(String email);
	public EnUser getUserDeletedByLogin(String email);
	public EnUser getUserForgotPasswordByLogin(String email);
	
	@Transactional
	public EnUser createUserLoginInitial(EnUser user);
	@Transactional
	public EnUser updateUser(EnUser user);
	@Transactional
	public  EnUser createUserBlocked(String email);
}
