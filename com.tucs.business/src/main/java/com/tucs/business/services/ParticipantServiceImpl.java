package com.tucs.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tucs.business.dao.interfaces.EnParticipantDao;
import com.tucs.business.services.interfaces.ParticipantService;
import com.tucs.core.model.entity.EnParticipant;

@Service
public class ParticipantServiceImpl implements ParticipantService{

	@Autowired EnParticipantDao participantDao;

	@Override
	public EnParticipant createParticipant(EnParticipant participant) {
		return null;
	}

	@Override
	public EnParticipant getParticipant(String participantId) {
		return participantDao.get(participantId);
	}

	@Override
	public EnParticipant updaParticipant(EnParticipant participant) {
		return null;
	}
	
	
}
