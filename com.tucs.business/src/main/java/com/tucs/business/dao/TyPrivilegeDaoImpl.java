package com.tucs.business.dao;

import com.tucs.business.dao.interfaces.TyPrivilegeDao;
import com.tucs.core.model.entity.profile.TyPrivilege;


public class TyPrivilegeDaoImpl extends AbstractBaseDao<TyPrivilege> implements TyPrivilegeDao {

	public TyPrivilegeDaoImpl() {
		super(TyPrivilege.class);
	}
	
}
