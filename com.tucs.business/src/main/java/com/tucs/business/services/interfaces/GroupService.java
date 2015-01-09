package com.tucs.business.services.interfaces;

import javax.transaction.Transactional;

import com.tucs.core.model.entity.EnGroup;



public interface GroupService extends BaseService{

	@Transactional
	public EnGroup createGroup(EnGroup group);

}
