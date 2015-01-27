package com.tucs.business.services.interfaces;

import java.util.List;

import javax.transaction.Transactional;

import com.tucs.core.commons.dto.AbstractMonthlyScreenDto;
import com.tucs.core.model.entity.EnControlMonthly;


public interface ControlMonthlyService extends BaseService {

	@Transactional
	public List<EnControlMonthly> createMonthly(EnControlMonthly... enControlMonthlys);

	public AbstractMonthlyScreenDto getMonthlyInitial(String controlId);
}
