package com.tucs.business.dao.interfaces;

import java.util.List;

import com.tucs.core.model.entity.EnControlMonthly;


public interface EnControlMonthlyDao extends BaseDao<EnControlMonthly>{

	public List<EnControlMonthly> getMonthlyClosed(String controlId);

}
