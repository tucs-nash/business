package com.tucs.business.dao;

import org.springframework.stereotype.Repository;

import com.tucs.business.dao.interfaces.BaseDao;
import com.tucs.core.model.entity.EnUser;

@Repository
public class EnUserDao extends AbstractBaseDao<EnUser> implements BaseDao<EnUser>{

	public EnUserDao() {
		super(EnUser.class);
	}

}
