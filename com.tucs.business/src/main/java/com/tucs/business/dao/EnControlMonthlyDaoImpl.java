package com.tucs.business.dao;

import org.springframework.stereotype.Repository;

import com.tucs.business.dao.interfaces.EnControlMonthlyDao;
import com.tucs.core.model.entity.EnControlMonthly;

@Repository
public class EnControlMonthlyDaoImpl extends AbstractBaseDao<EnControlMonthly> implements EnControlMonthlyDao{

	public EnControlMonthlyDaoImpl() {
		super(EnControlMonthly.class);
	}

}
