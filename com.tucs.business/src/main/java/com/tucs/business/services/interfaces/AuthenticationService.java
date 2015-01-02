package com.tucs.business.services.interfaces;

import com.tucs.core.model.entity.EnUser;


public interface AuthenticationService {
	public EnUser createUser(EnUser user);
	public Boolean verifyEmail(String email);
	public Boolean forgotPassword(String email);
}
