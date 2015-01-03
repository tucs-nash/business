package com.tucs.business.services.interfaces.security;

import com.tucs.business.services.interfaces.BaseService;
import com.tucs.core.model.entity.EnUser;


public interface AuthenticationService extends BaseService {
	public EnUser createUser(EnUser user);
	public Boolean verifyEmail(String email);
	public Boolean forgotPassword(String email);
}
