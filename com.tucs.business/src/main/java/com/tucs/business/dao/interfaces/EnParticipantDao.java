package com.tucs.business.dao.interfaces;

import java.util.List;

import com.tucs.core.model.entity.EnParticipant;


public interface EnParticipantDao extends BaseDao<EnParticipant> {

	public List<EnParticipant> getParticipantsControl(String controlId);
}
