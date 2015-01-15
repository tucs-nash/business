package com.tucs.business.services.interfaces;

import javax.transaction.Transactional;

import com.tucs.core.commons.dto.GroupParticipantLookupsDto;
import com.tucs.core.model.entity.EnGroup;
import com.tucs.core.model.entity.EnParticipant;
import com.tucs.core.model.entity.EnUser;



public interface GroupService extends BaseService{

	public EnGroup getGroupWithParticipant(String groupId);
	public EnGroup getGroup(String groupId);	
	public GroupParticipantLookupsDto getParticipantGroupLookup(String controlId);
	public EnParticipant getParticipant(String participantId);
	public EnParticipant createParticipantForGroup(EnParticipant enParticipant);
	
	@Transactional
	public EnGroup createGroup(EnGroup group, EnUser enUser);
	@Transactional
	public EnGroup updateGroup(EnGroup group, EnUser enUser);
	@Transactional
	public EnParticipant createParticipant(EnParticipant enParticipant, String email, EnUser enUser);
	@Transactional
	public EnParticipant updateParticipant(EnParticipant enParticipant, EnUser enUser);

}
