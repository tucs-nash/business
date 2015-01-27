package com.tucs.business.services;

import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tucs.business.dao.interfaces.EnCategoryDao;
import com.tucs.business.dao.interfaces.EnControlDao;
import com.tucs.business.dao.interfaces.EnControlMonthlyDao;
import com.tucs.business.dao.interfaces.EnGroupDao;
import com.tucs.business.dao.interfaces.EnParticipantDao;
import com.tucs.business.dao.interfaces.EnSavingsDao;
import com.tucs.business.dao.interfaces.TyCurrencyDao;
import com.tucs.business.services.interfaces.ControlMonthlyService;
import com.tucs.business.services.interfaces.ControlService;
import com.tucs.core.commons.dto.ControlDetailsDto;
import com.tucs.core.commons.dto.ControlLookupsDto;
import com.tucs.core.commons.enums.ProfileEnum;
import com.tucs.core.model.entity.EnCategory;
import com.tucs.core.model.entity.EnControl;
import com.tucs.core.model.entity.EnControl.TypeSplit;
import com.tucs.core.model.entity.EnControlMonthly;
import com.tucs.core.model.entity.EnGroup;
import com.tucs.core.model.entity.EnParticipant;
import com.tucs.core.model.entity.EnSavings;
import com.tucs.core.model.entity.EnUser;
import com.tucs.core.model.entity.profile.TyProfile;

@Service
public class ControlServiceImpl implements ControlService {

	@Autowired private ControlMonthlyService monthlyService;
	
	@Autowired private TyCurrencyDao currencyDao; 
	@Autowired private EnControlDao controlDao; 
	@Autowired private EnGroupDao groupDao; 
	@Autowired private EnParticipantDao participantDao;
	@Autowired private EnControlMonthlyDao controlMonthlyDao;
	@Autowired private EnCategoryDao categoryDao;
	@Autowired private EnSavingsDao savingsDao;
	
	@Override
	public ControlLookupsDto getControlLookups() {
		return new ControlLookupsDto(currencyDao.list());
	}

	@Override
	public EnControl getControl(String controlId) {
		return controlDao.get(controlId);
	}

	@Override
	public EnControl createControl(EnControl enControl, EnUser enUser) {
		enControl.setCreatedDate(LocalDateTime.now());
		enControl.setCreatedUser(enUser);
		enControl.setDeleted(false);
		if (enControl.getTypeSplit() == null) {
			enControl.setTypeSplit(TypeSplit.TYPE_SPLIT_PERSON);
		}
		
		controlDao.save(enControl);
		
		EnGroup groupParent = createAutomaticGroup(enControl, enUser, null, 1, enControl.getName());
		
		verifyShared(enControl, enUser, groupParent);
		verifySaving(enControl, enUser);
		createDefaultCategory(enControl, enUser);
		
		return enControl;
	}

	private void createDefaultCategory(EnControl enControl, EnUser enUser) {
		EnCategory category = new EnCategory();
		category.setBudget(0D);
		category.setControl(enControl);
		category.setCreatedDate(LocalDateTime.now());
		category.setCreatedUser(enUser);
		category.setName("Default");
		categoryDao.save(category);		
	}

	private void verifySaving(EnControl enControl, EnUser enUser) {
		if (enControl.getHasSaving()) {
			EnSavings enSavings = new EnSavings();
			enSavings.setBalance(enControl.getBalanceDefault());
			enSavings.setControl(enControl);
			enSavings.setCreatedUser(enUser);
			enSavings.setCreatedDate(LocalDateTime.now());
			savingsDao.save(enSavings);			
		}
	}

	private void verifyShared(EnControl enControl, EnUser enUser, EnGroup groupParent) {
		EnParticipant enParticipant = new EnParticipant();
		if (enControl.getShared()) {
			EnGroup groupChildMain = createAutomaticGroup(enControl, enUser, groupParent, 1, enControl.getName().concat(" 1"));
			createFirstMonthly(enControl, groupChildMain, enUser);
			enParticipant.setGroup(groupChildMain);			
			
			EnGroup groupChildOther = createAutomaticGroup(enControl, enUser, groupParent, 0, enControl.getName().concat(" 2"));
			createFirstMonthly(enControl, groupChildOther, enUser);
		} else {
			enParticipant.setGroup(groupParent);
			createFirstMonthly(enControl, groupParent, enUser);
		}
		enParticipant.setUser(enUser);
		enParticipant.setProfile(new TyProfile(ProfileEnum.ADMIN_GROUP.getId()));
		enParticipant.setCreatedDate(LocalDateTime.now());
		enParticipant.setCreatedUser(enUser);
		enParticipant.setDeleted(false);
		participantDao.save(enParticipant);

	}

	@Override
	public EnControl updateControl(EnControl enControl, EnUser enUser) {
		enControl.setUpdatedDate(LocalDateTime.now());
		enControl.setUpdatedUser(enUser);
		List<EnControlMonthly> currents = controlMonthlyDao.getCurrentMonthly(enControl.getId());
		
		for (EnControlMonthly current : currents) {
			if (!enControl.getStartDay().equals(new Long(current.getStartDate().getDayOfMonth()))) {
				int diff =  current.getStartDate().getDayOfMonth() - enControl.getStartDay().intValue();
				
				if (diff >= 16) {
					diff -= current.getStartDate().dayOfMonth().withMaximumValue().getDayOfMonth();
				} else if (diff < -16) {
					diff += current.getStartDate().dayOfMonth().withMaximumValue().getDayOfMonth();
				}
				current.setEndDate(current.getEndDate().minusDays(diff));
				
				if (current.getEndDate().isBefore(LocalDate.now())) {
					monthlyService.createMonthly(current);
				} else {				
					controlMonthlyDao.update(current);
				}
			}			
		}
		return controlDao.update(enControl);
	}

	@Override
	public ControlDetailsDto getControlDetails(String controlId) {
		ControlDetailsDto controlDetails = new ControlDetailsDto();
		controlDetails.setCategories(categoryDao.getCategoriesByControl(controlId, null));
		controlDetails.setControl(controlDetails.getCategories().get(0).getControl());
		
		if (controlDetails.getControl().getShared()) {
			controlDetails.setGroups(groupDao.getGroupsControl(controlId));
		} else {
			controlDetails.setParticipants(participantDao.getParticipantsControl(controlId));
		}
		
		if (controlDetails.getControl().getHasClosing()) {
			controlDetails.setClosings(controlMonthlyDao.getMonthlyClosed(controlId));
		}
		
		if (controlDetails.getControl().getHasSaving()) {
			controlDetails.setSavings(savingsDao.getSavingByControl(controlId));
		}
					
		return controlDetails;
	}

	private EnGroup createAutomaticGroup(EnControl control, EnUser enUser, EnGroup groupParent,long amount, String name) {
		EnGroup group = new EnGroup();
		group.setControl(control);
		group.setCreatedDate(LocalDateTime.now());
		group.setCreatedUser(enUser);
		group.setName(name);
		group.setDeleted(false);
		group.setAmountParticipant(amount);
		group.setGroupParent(groupParent);
		return groupDao.save(group);
	}

	private EnControlMonthly createFirstMonthly(EnControl enControl, EnGroup groupParent, EnUser enUser) {
		EnControlMonthly monthly = new EnControlMonthly();
		monthly.setBalanceExpense(0D);
		monthly.setBalanceRevenue(0D);
		monthly.setClosed(false);
		monthly.setCurrentMonthly(true);
		monthly.setControl(enControl);
		monthly.setGroup(groupParent);
		
		setDateFieldsMonthly(monthly);
		return controlMonthlyDao.save(monthly);
	}

	private void setDateFieldsMonthly(EnControlMonthly monthly) {
		LocalDate today = LocalDate.now();
		int day = monthly.getControl().getStartDay().intValue();
		int month = today.getMonthOfYear();
		int year = today.getYear();

		if (day > today.dayOfMonth().withMaximumValue().getDayOfMonth()) {
			day = today.dayOfMonth().withMaximumValue().getDayOfMonth();
		}
		
		// look test_start_day.txt
		if (monthly.getControl().getStartDay() < 20) {
			if (monthly.getControl().getStartDay() > today.getDayOfMonth()) {
				month = today.minusMonths(1).getMonthOfYear();
				year = today.minusMonths(1).getYear();
			} 
			monthly.setStartDate(new LocalDate(year, month, day));
		} else {
			if (monthly.getControl().getStartDay() > today.getDayOfMonth()) {
				monthly.setStartDate(new LocalDate(year, month, day).minusMonths(1));
			} else {
				monthly.setStartDate(new LocalDate(year, month, day));
				month = today.plusMonths(1).getMonthOfYear();
				year = today.plusMonths(1).getYear();
			}
		}
		
		monthly.setMonth(new Long(month));
		monthly.setYear(new Long(year));
		monthly.setEndDate(monthly.getStartDate().plusMonths(1).minusDays(1));
		
	}
}
