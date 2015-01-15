package com.tucs.business.services;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tucs.business.dao.interfaces.EnControlDao;
import com.tucs.business.dao.interfaces.EnGroupDao;
import com.tucs.business.dao.interfaces.EnParticipantDao;
import com.tucs.business.dao.interfaces.TyProfileDao;
import com.tucs.business.services.interfaces.GroupService;
import com.tucs.business.services.interfaces.security.UserServiceAuthentication;
import com.tucs.core.commons.dto.GroupParticipantLookupsDto;
import com.tucs.core.model.entity.EnGroup;
import com.tucs.core.model.entity.EnParticipant;
import com.tucs.core.model.entity.EnUser;

@Service
public class GroupServiceImpl implements GroupService{

	@Autowired UserServiceAuthentication userService;
	
	@Autowired EnGroupDao groupDao;
	@Autowired EnParticipantDao enParticipantDao;
	@Autowired EnControlDao controlDao;
	@Autowired TyProfileDao profileDao;
	
	@Override
	public EnGroup createGroup(EnGroup group, EnUser user) {
		return groupDao.save(group);
	}

	@Override
	public EnGroup getGroup(String groupId) {
		return groupDao.get(groupId);
	}

	
	@Override
	public EnGroup getGroupWithParticipant(String groupId) {
		return groupDao.getGroupWithParticipant(groupId);
	}

	@Override
	public EnGroup updateGroup(EnGroup group, EnUser user) {
		return groupDao.update(group);
	}

	@Override
	public EnParticipant createParticipantForGroup(EnParticipant enParticipant) {
		EnUser user = verifyParticipant(enParticipant.getUser().getEmail(), enParticipant.getGroup().getId());
		enParticipant.setUser(user);
		return enParticipant;
	}
	
	@Override
	public GroupParticipantLookupsDto getParticipantGroupLookup(String controlId) {
		GroupParticipantLookupsDto dto = new GroupParticipantLookupsDto();
		dto.setNameControl(controlDao.get(controlId).getName());
		dto.setProfiles(profileDao.list());
		dto.setGroup(groupDao.getMainGroupControl(controlId));
		return dto;
	}

	@Override
	public EnParticipant getParticipant(String participantId) {
		return enParticipantDao.get(participantId);
	}

	@Override
	public EnParticipant createParticipant(EnParticipant enParticipant, String email, EnUser enUser) {
		EnUser user = verifyParticipant(email, enParticipant.getGroup().getId());	
		if (user == null) {
			return null;
		}
		enParticipant.setUser(user);
		enParticipant.setCreatedUser(enUser);
		enParticipant.setCreatedDate(LocalDateTime.now());
		enParticipant.setDeleted(false);
		return enParticipantDao.save(enParticipant);
	}

	@Override
	public EnParticipant updateParticipant(EnParticipant enParticipant, EnUser enUser) {
		enParticipant.setUpdatedUser(enUser);
		enParticipant.setUpdatedDate(LocalDateTime.now());
		return enParticipantDao.update(enParticipant);
	}

	private EnUser verifyParticipant(String email, String groupId) {
		EnUser user  = userService.getUserByLogin(email);
		if (user == null) {
			user = userService.createUserBlocked(email);
		} else if (enParticipantDao.verifyParticipant(user, groupId)) {
			user = null;
		}	
		return user;
	}
}
