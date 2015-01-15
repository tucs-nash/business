package com.tucs.business.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
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
	public List<EnCategory> getCategoriesByControl(String controlId, String categoryId) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<EnCategory> criteriaQuery = criteriaBuilder.createQuery(EnCategory.class);
		
		Root<EnCategory> from = criteriaQuery.from(EnCategory.class);
		from.fetch("parent", JoinType.LEFT);
		
		List<Predicate> where = new ArrayList<Predicate>();
		where.add(criteriaBuilder.equal(from.get("control"), new EnControl(controlId)));
		
		if (categoryId != null && !categoryId.isEmpty()) {
			where.add(criteriaBuilder.notEqual(from.get("id"), categoryId));
		}
		
		criteriaQuery.select(from);
		criteriaQuery.where(where.toArray(new Predicate[where.size()]));
		TypedQuery<EnCategory> query = getEntityManager().createQuery(criteriaQuery);
		return query.getResultList();
	}
}
