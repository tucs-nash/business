package com.tucs.business.services;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tucs.business.dao.interfaces.EnControlDao;
import com.tucs.business.dao.interfaces.EnControlMonthlyDao;
import com.tucs.business.dao.interfaces.EnGroupDao;
import com.tucs.business.dao.interfaces.EnParticipantDao;
import com.tucs.business.dao.interfaces.TyCurrencyDao;
import com.tucs.business.services.interfaces.ControlService;
import com.tucs.core.commons.dto.ControlDetailsDto;
import com.tucs.core.commons.dto.ControlLookupsDto;
import com.tucs.core.commons.enums.ProfileEnum;
import com.tucs.core.model.entity.EnControl;
import com.tucs.core.model.entity.EnControl.TypeSplit;
import com.tucs.core.model.entity.EnControlMonthly;
import com.tucs.core.model.entity.EnGroup;
import com.tucs.core.model.entity.EnParticipant;
import com.tucs.core.model.entity.EnUser;
import com.tucs.core.model.entity.profile.TyProfile;

@Service
public class ControlServiceImpl implements ControlService {

	@Autowired private TyCurrencyDao currencyDao; 
	@Autowired private EnControlDao controlDao; 
	@Autowired private EnGroupDao groupDao; 
	@Autowired private EnParticipantDao participantDao;
	@Autowired private EnControlMonthlyDao controlMonthlyDao;
	
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
		
		EnControl controlCreated = controlDao.save(enControl);
		
		EnGroup groupParent = createAutomaticGroup(enControl, enUser, null);
		EnParticipant enParticipant = new EnParticipant();		
		if (controlCreated.getShared()) {
			EnGroup groupChild = createAutomaticGroup(enControl, enUser, groupParent);
			enParticipant.setGroup(groupChild);
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
		
		return controlCreated;
	}

	@Override
	public EnControl updateControl(EnControl enControl, EnUser enUser) {
		enControl.setUpdatedDate(LocalDateTime.now());
		enControl.setUpdatedUser(enUser);
		return controlDao.update(enControl);
	}

	@Override
	public ControlDetailsDto getControlDetails(String controlId) {
		ControlDetailsDto controlDetails = new ControlDetailsDto();
		controlDetails.setControl(controlDao.get(controlId));
		
		if (controlDetails.getControl().getShared()) {
			controlDetails.setGroups(groupDao.getGroupsControl(controlId));
		} else {
			controlDetails.setParticipants(participantDao.getParticipantsControl(controlId));
		}
		
		return controlDetails;
	}

	private EnGroup createAutomaticGroup(EnControl control, EnUser enUser, EnGroup groupParent) {
		EnGroup group = new EnGroup();
		group.setControl(control);
		group.setCreatedDate(LocalDateTime.now());
		group.setCreatedUser(enUser);
		group.setOwnerUser(enUser);
		group.setName(control.getName());
		group.setDeleted(false);
		group.setAmountParticipant(1L);
		group.setGroupParent(groupParent);
		return groupDao.save(group);
	}

	private EnControlMonthly createFirstMonthly(EnControl enControl, EnGroup groupParent, EnUser enUser) {
		LocalDate today = LocalDate.now();
		EnControlMonthly monthly = new EnControlMonthly();
		monthly.setBalanceExpense(0D);
		monthly.setBalanceRevenue(enControl.getHasSaving() ? enControl.getBalanceDefault() : 0D);
		monthly.setClosed(false);
		monthly.setCurrentMonthly(true);
		monthly.setControl(enControl);
		monthly.setGroup(groupParent);
		
		int day = enControl.getStartDay().intValue();
		int month = today.getMonthOfYear();
		int year = today.getYear();

		if (day > today.dayOfMonth().withMaximumValue().getDayOfMonth()) {
			day = today.dayOfMonth().withMaximumValue().getDayOfMonth();
		}
		
		// look test_start_day.txt
		if (enControl.getStartDay() < 20) {
			if (enControl.getStartDay() > today.getDayOfMonth()) {
				month = today.minusMonths(1).getMonthOfYear();
				year = today.minusMonths(1).getYear();
			} 
			monthly.setStartDate(new LocalDate(year, month, day));
		} else {
			if (enControl.getStartDay() > today.getDayOfMonth()) {
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
		return controlMonthlyDao.save(monthly);
	}
}
