package com.tucs.business.services.interfaces;

import javax.transaction.Transactional;

import com.tucs.core.commons.dto.ControlDetailsDto;
import com.tucs.core.commons.dto.ControlLookupsDto;
import com.tucs.core.model.entity.EnControl;
import com.tucs.core.model.entity.EnUser;


public interface ControlService extends BaseService {

	public ControlLookupsDto getControlLookups();
	public ControlDetailsDto getControlDetails(String controlId);
	public EnControl getControl(String controlId);
	
	@Transactional
	public EnControl createControl(EnControl enControl, EnUser enUser);
	@Transactional
	public EnControl updateControl(EnControl enControl, EnUser enUser);
	
}
