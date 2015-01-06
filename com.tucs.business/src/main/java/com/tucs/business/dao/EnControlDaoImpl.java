package com.tucs.business.dao;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.tucs.business.dao.interfaces.EnControlDao;
import com.tucs.core.model.entity.EnControl;
import com.tucs.core.model.entity.EnGroup;
import com.tucs.core.model.entity.EnParticipant;
import com.tucs.core.model.entity.EnUser;

@Repository
public class EnControlDaoImpl extends AbstractBaseDao<EnControl> implements EnControlDao{

	public EnControlDaoImpl() {
		super(EnControl.class);
	}

	@Override
	public List<EnControl> listUserControl(EnUser user) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<EnControl> criteriaQuery = criteriaBuilder.createQuery(EnControl.class);
		
		Root<EnControl> from = criteriaQuery.from(EnControl.class);
		Join<EnControl, EnGroup> joinGroup = from.join("enGroups");
		Join<EnGroup, EnParticipant> joinParticipant = joinGroup.join("enParticipants");
		
		criteriaQuery.where(criteriaBuilder.equal(joinParticipant.get("user"), user), criteriaBuilder.equal(from.get("deleted"), false));
		TypedQuery<EnControl> typedQuery = getEntityManager().createQuery(criteriaQuery);		
		
		return typedQuery.getResultList();
	}

}
