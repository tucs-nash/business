package com.tucs.business.dao.security.interfaces;

import com.tucs.business.dao.interfaces.BaseDao;
import com.tucs.core.commons.enums.TypeGetUser;
import com.tucs.core.model.entity.EnUser;


public interface UserAuthenticationDao extends BaseDao<EnUser> {
	
	public EnUser getUserByLogin(String email, TypeGetUser type);
	public EnUser getUserByLoginPassword(String email, String password);
	public Boolean verifyEmail(String email);
}
