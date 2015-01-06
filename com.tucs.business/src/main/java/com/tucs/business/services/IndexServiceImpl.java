package com.tucs.business.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tucs.business.dao.interfaces.EnControlDao;
import com.tucs.business.services.interfaces.IndexService;
import com.tucs.core.model.entity.EnControl;
import com.tucs.core.model.entity.EnUser;

@Service
public class IndexServiceImpl implements IndexService {
	
	@Autowired 
	private EnControlDao controlDao;

	@Override
	public List<EnControl> verifyInitialControl(String userId) {
		return controlDao.listUserControl(new EnUser(userId));
	}
	
	
}
