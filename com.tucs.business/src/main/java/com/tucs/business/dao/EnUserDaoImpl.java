package com.tucs.business.dao;

import org.springframework.stereotype.Repository;

import com.tucs.business.dao.interfaces.EnUserDao;
import com.tucs.core.model.entity.EnUser;

@Repository
public class EnUserDaoImpl extends AbstractBaseDao<EnUser> implements EnUserDao{

	public EnUserDaoImpl() {
		super(EnUser.class);
	}

}
