package com.tucs.business.dao.interfaces;

import java.util.List;

import com.tucs.core.model.entity.EnControlMonthly;
import com.tucs.core.model.entity.EnTransaction;


public interface EnTransactionDao extends BaseDao<EnTransaction>{

	List<EnTransaction> getTransactionByControlsMonthly(EnControlMonthly... controls);
}
