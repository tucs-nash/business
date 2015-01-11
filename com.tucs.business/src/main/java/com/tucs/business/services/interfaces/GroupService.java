package com.tucs.business.services.interfaces;

import javax.transaction.Transactional;

import com.tucs.core.model.entity.EnGroup;



public interface GroupService extends BaseService{

	public EnGroup getGroup(String groupId);
	
	@Transactional
	public EnGroup createGroup(EnGroup group);
	@Transactional
	public EnGroup updateGroup(EnGroup group);

}
