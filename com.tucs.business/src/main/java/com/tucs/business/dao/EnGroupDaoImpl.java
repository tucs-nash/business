package com.tucs.business.dao;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.tucs.business.dao.interfaces.EnGroupDao;
import com.tucs.core.model.entity.EnControl;
import com.tucs.core.model.entity.EnGroup;

@Repository
public class EnGroupDaoImpl extends AbstractBaseDao<EnGroup> implements EnGroupDao {

	public EnGroupDaoImpl() {
		super(EnGroup.class);
	}

	@Override
	public List<EnGroup> getGroupsControl(String controlId) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<EnGroup> criteriaQuery = criteriaBuilder.createQuery(EnGroup.class);
		
		Root<EnGroup> from = criteriaQuery.from(EnGroup.class);
		
		criteriaQuery.where(criteriaBuilder.equal(from.get("control"), new EnControl(controlId))
				, criteriaBuilder.equal(from.get("deleted"), false)
				, criteriaBuilder.isNotNull(from.get("groupParent")));
		TypedQuery<EnGroup> typedQuery = getEntityManager().createQuery(criteriaQuery);		
		
		return typedQuery.getResultList();
	}
	
}
