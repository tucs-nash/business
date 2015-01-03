package com.tucs.business.services;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tucs.business.dao.EnUserDaoImpl;
import com.tucs.business.services.interfaces.UserService;
import com.tucs.core.model.entity.EnUser;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private EnUserDaoImpl userDao;
	
	@Override
	public EnUser getUser(String userId) {
		return userDao.get(userId);
	}

	@Override
	public EnUser updateUser(EnUser user) {
		user.setUpdatedDate(LocalDateTime.now());
		return userDao.update(user);
	}
	
}
