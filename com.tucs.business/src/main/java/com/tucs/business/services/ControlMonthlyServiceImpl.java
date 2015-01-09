package com.tucs.business.services;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tucs.business.dao.interfaces.EnControlMonthlyDao;
import com.tucs.business.services.interfaces.ControlMonthlyService;
import com.tucs.core.model.entity.EnControlMonthly;

@Service
public class ControlMonthlyServiceImpl implements ControlMonthlyService {

	@Autowired EnControlMonthlyDao controlMonthlyDao;

	@Override
	public void createMonthly(EnControlMonthly... enControlMonthlys) {
		for (EnControlMonthly previousControlMonthly : enControlMonthlys) {			
			EnControlMonthly monthly = new EnControlMonthly();
			monthly.setBalanceExpense(previousControlMonthly.getControl().getShared() ? previousControlMonthly.getBalanceExpense() : 0D);
			monthly.setBalanceRevenue(previousControlMonthly.getControl().getHasSaving() ? previousControlMonthly.getControl().getBalanceDefault() : 0D);
			monthly.setClosed(false);
			monthly.setCurrentMonthly(true);
			monthly.setControl(previousControlMonthly.getControl());
			monthly.setGroup(previousControlMonthly.getGroup());
			
			LocalDate auxMonthYear = new LocalDate(1, monthly.getMonth().intValue(), monthly.getYear().intValue()).plusMonths(1);
			monthly.setMonth(new Long(auxMonthYear.getMonthOfYear()));
			monthly.setYear(new Long(auxMonthYear.getYear()));
			monthly.setStartDate(previousControlMonthly.getStartDate().plusMonths(1));
			monthly.setEndDate(monthly.getStartDate().plusMonths(1).minusDays(1));
			
			controlMonthlyDao.save(monthly);
			
			previousControlMonthly.setCurrentMonthly(false);
			previousControlMonthly.setUpdatedDate(LocalDateTime.now());
			controlMonthlyDao.update(previousControlMonthly);
		}
	}
}
