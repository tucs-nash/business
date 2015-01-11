package com.tucs.business.services.interfaces;

import javax.transaction.Transactional;

import com.tucs.core.model.entity.EnParticipant;



public interface ParticipantService extends BaseService{

	public EnParticipant getParticipant(String participantId);

	@Transactional
	public EnParticipant createParticipant(EnParticipant participant);
	@Transactional
	public EnParticipant updaParticipant(EnParticipant participant);

}
