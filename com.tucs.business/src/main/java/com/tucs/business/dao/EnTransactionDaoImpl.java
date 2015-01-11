package com.tucs.business.dao;

import org.springframework.stereotype.Repository;

import com.tucs.business.dao.interfaces.EnTransactionDao;
import com.tucs.core.model.entity.EnTransaction;

@Repository
public class EnTransactionDaoImpl extends AbstractBaseDao<EnTransaction> implements EnTransactionDao{

	public EnTransactionDaoImpl() {
		super(EnTransaction.class);
	}
}
