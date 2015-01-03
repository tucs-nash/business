package com.tucs.business.dao;

import org.hibernate.usertype.EnhancedUserType;
import org.springframework.stereotype.Repository;

import com.tucs.business.dao.interfaces.BaseDao;
import com.tucs.business.dao.interfaces.EnUserDao;
import com.tucs.core.model.entity.EnUser;

@Repository
public class EnUserDaoImpl extends AbstractBaseDao<EnUser> implements EnUserDao{

	public EnUserDaoImpl() {
		super(EnUser.class);
	}

}
