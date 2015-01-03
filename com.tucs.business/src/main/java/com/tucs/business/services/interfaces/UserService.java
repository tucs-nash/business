package com.tucs.business.services.interfaces;

import com.tucs.core.model.entity.EnUser;


public interface UserService extends BaseService {

	public EnUser getUser(String userId);
	public EnUser updateUser(EnUser user);
}
