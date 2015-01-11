package com.tucs.business.services.interfaces.security;

import com.tucs.business.services.interfaces.BaseService;
import com.tucs.core.commons.dto.UserLookupsDto;
import com.tucs.core.model.entity.EnUser;


public interface AuthenticationFilter extends BaseService {
	public EnUser createUser(EnUser user);
	public EnUser getUser(String userId);
	public EnUser updateUser(EnUser user);
	public Boolean verifyEmail(String email);
	public Boolean forgotPassword(String email);
	public UserLookupsDto getUserLookups();
}
