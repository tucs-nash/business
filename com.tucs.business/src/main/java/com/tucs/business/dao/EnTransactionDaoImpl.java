package com.tucs.business.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.tucs.business.dao.interfaces.EnTransactionDao;
import com.tucs.core.model.entity.EnControlMonthly;
import com.tucs.core.model.entity.EnTransaction;

@Repository
public class EnTransactionDaoImpl extends AbstractBaseDao<EnTransaction> implements EnTransactionDao{

	public EnTransactionDaoImpl() {
		super(EnTransaction.class);
	}

	@Override
	public List<EnTransaction> getTransactionByControlsMonthly(EnControlMonthly... controls) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<EnTransaction> criteriaQuery = criteriaBuilder.createQuery(EnTransaction.class);
		
		Root<EnTransaction> from = criteriaQuery.from(EnTransaction.class);
		Join<EnTransaction, EnControlMonthly> joinMonthly = from.join("controlMonthly");
		ArrayList<String> monthlys = new ArrayList<String>();
		
		for (EnControlMonthly monthly : controls) {
			monthlys.add(monthly.getId());
		}
		criteriaQuery.where(joinMonthly.get("id").in(monthlys));
		TypedQuery<EnTransaction> query = getEntityManager().createQuery(criteriaQuery);
		return query.getResultList();
	}
}
