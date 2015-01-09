package com.tucs.business.dao;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.tucs.business.dao.interfaces.EnParticipantDao;
import com.tucs.core.model.entity.EnControl;
import com.tucs.core.model.entity.EnGroup;
import com.tucs.core.model.entity.EnParticipant;

@Repository
public class EnParticipantDaoImpl extends AbstractBaseDao<EnParticipant> implements EnParticipantDao {

	public EnParticipantDaoImpl() {
		super(EnParticipant.class);
	}

	@Override
	public List<EnParticipant> getParticipantsControl(String controlId) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<EnParticipant> criteriaQuery = criteriaBuilder.createQuery(EnParticipant.class);
		
		Root<EnParticipant> from = criteriaQuery.from(EnParticipant.class);
		Join<EnParticipant, EnGroup> joinGroup = from.join("group");
		
		criteriaQuery.where(criteriaBuilder.equal(joinGroup.get("control"), new EnControl(controlId))
				, criteriaBuilder.equal(joinGroup.get("deleted"), false)
				, criteriaBuilder.isNull(joinGroup.get("groupParent")));
		TypedQuery<EnParticipant> typedQuery = getEntityManager().createQuery(criteriaQuery);		
		
		return typedQuery.getResultList();
	}
	
}
