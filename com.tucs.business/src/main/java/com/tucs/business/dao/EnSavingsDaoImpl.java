package com.tucs.business.dao;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.tucs.business.dao.interfaces.EnSavingsDao;
import com.tucs.core.model.entity.EnControl;
import com.tucs.core.model.entity.EnSavings;

@Repository
public class EnSavingsDaoImpl extends AbstractBaseDao<EnSavings> implements EnSavingsDao{

	public EnSavingsDaoImpl() {
		super(EnSavings.class);
	}

	@Override
	public EnSavings getSavingByControl(String controlId) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<EnSavings> criteriaQuery = criteriaBuilder.createQuery(EnSavings.class);
		
		Root<EnSavings> from = criteriaQuery.from(EnSavings.class);
		criteriaQuery.where(criteriaBuilder.equal(from.get("control"), new EnControl(controlId)));
		TypedQuery<EnSavings> query = getEntityManager().createQuery(criteriaQuery);
		return query.getSingleResult();
	}
}
