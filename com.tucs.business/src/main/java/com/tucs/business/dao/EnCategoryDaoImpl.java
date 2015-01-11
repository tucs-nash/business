package com.tucs.business.dao;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.tucs.business.dao.interfaces.EnCategoryDao;
import com.tucs.core.model.entity.EnCategory;
import com.tucs.core.model.entity.EnControl;

@Repository
public class EnCategoryDaoImpl extends AbstractBaseDao<EnCategory> implements EnCategoryDao{

	public EnCategoryDaoImpl() {
		super(EnCategory.class);
	}
	
	@Override
	public List<EnCategory> getCategoriesByControl(String controlId) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<EnCategory> criteriaQuery = criteriaBuilder.createQuery(EnCategory.class);
		
		Root<EnCategory> from = criteriaQuery.from(EnCategory.class);
		from.fetch("parent", JoinType.LEFT);
		
		criteriaQuery.select(from);
		criteriaQuery.where(criteriaBuilder.equal(from.get("control"), new EnControl(controlId)));
		TypedQuery<EnCategory> query = getEntityManager().createQuery(criteriaQuery);
		return query.getResultList();
	}
}
