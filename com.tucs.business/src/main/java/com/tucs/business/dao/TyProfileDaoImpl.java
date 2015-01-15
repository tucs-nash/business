package com.tucs.business.dao;

import org.springframework.stereotype.Repository;

import com.tucs.business.dao.interfaces.TyProfileDao;
import com.tucs.core.model.entity.profile.TyProfile;

@Repository
public class TyProfileDaoImpl extends AbstractBaseDao<TyProfile> implements	TyProfileDao {

	public TyProfileDaoImpl() {
		super(TyProfile.class);
	}
}
