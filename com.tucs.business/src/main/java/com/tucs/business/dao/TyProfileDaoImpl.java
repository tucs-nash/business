package com.tucs.business.dao;

import com.tucs.business.dao.interfaces.TyProfileDao;
import com.tucs.core.model.entity.profile.TyProfile;


public class TyProfileDaoImpl extends AbstractBaseDao<TyProfile> implements	TyProfileDao {

	public TyProfileDaoImpl() {
		super(TyProfile.class);
	}
}
