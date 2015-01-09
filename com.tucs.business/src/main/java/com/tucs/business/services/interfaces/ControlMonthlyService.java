package com.tucs.business.services.interfaces;

import javax.transaction.Transactional;

import com.tucs.core.model.entity.EnControlMonthly;


public interface ControlMonthlyService extends BaseService {

	@Transactional
	public void createMonthly(EnControlMonthly... enControlMonthlys);
}
