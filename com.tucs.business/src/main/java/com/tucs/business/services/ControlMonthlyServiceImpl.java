package com.tucs.business.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.YearMonth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tucs.business.dao.interfaces.EnControlMonthlyDao;
import com.tucs.business.dao.interfaces.EnTransactionDao;
import com.tucs.business.services.interfaces.CategoryService;
import com.tucs.business.services.interfaces.ControlMonthlyService;
import com.tucs.core.commons.dto.AbstractMonthlyScreenDto;
import com.tucs.core.commons.dto.CategoryTransactionsDto;
import com.tucs.core.commons.dto.NormalMonthlyScreenDto;
import com.tucs.core.commons.dto.SharedMonthlyScreenDto;
import com.tucs.core.model.entity.EnCategory;
import com.tucs.core.model.entity.EnControlMonthly;
import com.tucs.core.model.entity.EnTransaction;

@Service
public class ControlMonthlyServiceImpl implements ControlMonthlyService {

	@Autowired CategoryService categoryService;
	
	@Autowired EnTransactionDao transactionDao;
	@Autowired EnControlMonthlyDao controlMonthlyDao;

	@Override
	public List<EnControlMonthly> createMonthly(EnControlMonthly... enControlMonthlys) {
		List<EnControlMonthly> news = new ArrayList<EnControlMonthly>();
		for (EnControlMonthly previousControlMonthly : enControlMonthlys) {			
			EnControlMonthly nextMonthly = createNextMonthly(previousControlMonthly);
			
			news.add(nextMonthly);			
			previousControlMonthly.setCurrentMonthly(false);
			previousControlMonthly.setUpdatedDate(LocalDateTime.now());
			controlMonthlyDao.update(previousControlMonthly);
		}
		return news;
	}

	private EnControlMonthly createNextMonthly(EnControlMonthly previousControlMonthly) {
		EnControlMonthly monthly = new EnControlMonthly();
		monthly.setBalanceExpense(previousControlMonthly.getControl().getShared() ? previousControlMonthly.getBalanceExpense() : 0D);
		monthly.setBalanceRevenue(previousControlMonthly.getControl().getHasSaving() ? previousControlMonthly.getControl().getBalanceDefault() : 0D);
		monthly.setClosed(false);
		monthly.setCurrentMonthly(true);
		monthly.setControl(previousControlMonthly.getControl());
		monthly.setGroup(previousControlMonthly.getGroup());
		
		YearMonth auxMonthYear = new YearMonth(monthly.getYear().intValue(), monthly.getMonth().intValue()).plusMonths(1);
		monthly.setMonth(new Long(auxMonthYear.getMonthOfYear()));
		monthly.setYear(new Long(auxMonthYear.getYear()));
		monthly.setStartDate(previousControlMonthly.getStartDate().plusMonths(1));
		monthly.setEndDate(monthly.getStartDate().plusMonths(1).minusDays(1));
		
		return controlMonthlyDao.save(monthly);		
	}

	@Override
	public AbstractMonthlyScreenDto getMonthlyInitial(String controlId) {
		List<EnControlMonthly> controlMonthlies = controlMonthlyDao.getCurrentMonthly(controlId);

		if(LocalDate.now().isAfter(controlMonthlies.get(0).getEndDate())) {
			controlMonthlies = createMonthly(controlMonthlies.toArray(new EnControlMonthly[controlMonthlies.size()]));
		}

		return createMonthlyScreenDto(controlMonthlies);
	}

	private AbstractMonthlyScreenDto createMonthlyScreenDto(List<EnControlMonthly> controlMonthlies) {
		if (controlMonthlies.size() > 1) {
			SharedMonthlyScreenDto shared = new SharedMonthlyScreenDto(controlMonthlies);
			shared.setTransactions(transactionDao.getTransactionByControlsMonthly(controlMonthlies.toArray(new EnControlMonthly[controlMonthlies.size()])));
			return shared;
		} else {
			NormalMonthlyScreenDto normal = new NormalMonthlyScreenDto(controlMonthlies.get(0));
			normal.setCategories(getCategoriesTransactionDto(normal.getMonthly()));
			return normal;
		}
	}
	
	private List<CategoryTransactionsDto> getCategoriesTransactionDto(EnControlMonthly monthly) {
		HashMap<EnCategory, CategoryTransactionsDto> categories = new HashMap<EnCategory, CategoryTransactionsDto>(0);
		List<EnTransaction> transactions = transactionDao.getTransactionByControlsMonthly(monthly);
		
		for (EnTransaction transaction : transactions) {
			CategoryTransactionsDto category = categories.get(transaction.getCategory());
			
			if (category == null) {
				category = new CategoryTransactionsDto();
				categories.put(transaction.getCategory(), category);
			}
			category.addTransactions(transaction);
		}
		
		List<CategoryTransactionsDto> response = new ArrayList<CategoryTransactionsDto>();
		response.addAll(categories.values());
		return (List<CategoryTransactionsDto>) response;
	}	
}
