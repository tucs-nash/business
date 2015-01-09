package com.tucs.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tucs.business.dao.interfaces.EnGroupDao;
import com.tucs.business.services.interfaces.GroupService;
import com.tucs.core.model.entity.EnGroup;

@Service
public class GroupServiceImpl implements GroupService{

	@Autowired EnGroupDao groupDao;
	
	@Override
	public EnGroup createGroup(EnGroup group) {
		return groupDao.save(group);
	}
	
}
