package com.tucs.business.dao;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.tucs.business.dao.interfaces.EnControlMonthlyDao;
import com.tucs.core.model.entity.EnControl;
import com.tucs.core.model.entity.EnControlMonthly;

@Repository
public class EnControlMonthlyDaoImpl extends AbstractBaseDao<EnControlMonthly> implements EnControlMonthlyDao{

	public EnControlMonthlyDaoImpl() {
		super(EnControlMonthly.class);
	}

	@Override
	public List<EnControlMonthly> getMonthlyClosed(String controlId) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<EnControlMonthly> criteriaQuery = criteriaBuilder.createQuery(EnControlMonthly.class);
		
		Root<EnControlMonthly> from = criteriaQuery.from(EnControlMonthly.class);
		
		criteriaQuery.where(criteriaBuilder.equal(from.get("control"), new EnControl(controlId)),
				criteriaBuilder.equal(from.get("closed"), true));
		TypedQuery<EnControlMonthly> query = getEntityManager().createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	@Override
	public List<EnControlMonthly> getCurrentMonthly(String controlId) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<EnControlMonthly> criteriaQuery = criteriaBuilder.createQuery(EnControlMonthly.class);
		
		Root<EnControlMonthly> from = criteriaQuery.from(EnControlMonthly.class);
		
		criteriaQuery.where(criteriaBuilder.equal(from.get("control"), new EnControl(controlId)),
				criteriaBuilder.equal(from.get("currentMonthly"), true));
		TypedQuery<EnControlMonthly> query = getEntityManager().createQuery(criteriaQuery);
		
		return query.getResultList();
	}

}
