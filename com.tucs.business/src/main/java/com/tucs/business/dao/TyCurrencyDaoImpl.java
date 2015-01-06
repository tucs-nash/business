package com.tucs.business.dao;

import org.springframework.stereotype.Repository;

import com.tucs.business.dao.interfaces.TyCurrencyDao;
import com.tucs.core.model.entity.TyCurrency;

@Repository
public class TyCurrencyDaoImpl extends AbstractBaseDao<TyCurrency> implements TyCurrencyDao {

	public TyCurrencyDaoImpl() {
		super(TyCurrency.class);
	}
}
