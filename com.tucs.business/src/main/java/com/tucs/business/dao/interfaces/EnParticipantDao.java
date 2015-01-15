package com.tucs.business.dao.interfaces;

import java.util.List;

import com.tucs.core.model.entity.EnParticipant;
import com.tucs.core.model.entity.EnUser;


public interface EnParticipantDao extends BaseDao<EnParticipant> {

	public List<EnParticipant> getParticipantsControl(String controlId);
	public Boolean verifyParticipant(EnUser user, String groupId);
}
