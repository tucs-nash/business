package com.tucs.business.dao;

import org.springframework.stereotype.Repository;

import com.tucs.business.dao.interfaces.EnParticipantDao;
import com.tucs.core.model.entity.EnParticipant;

@Repository
public class EnParticipantDaoImpl extends AbstractBaseDao<EnParticipant> implements EnParticipantDao {

	public EnParticipantDaoImpl() {
		super(EnParticipant.class);
	}
	
}
