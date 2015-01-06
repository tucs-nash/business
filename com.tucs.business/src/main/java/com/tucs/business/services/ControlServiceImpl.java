package com.tucs.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tucs.business.dao.interfaces.TyCurrencyDao;
import com.tucs.business.services.interfaces.ControlService;
import com.tucs.core.commons.dto.ControlLookupsDto;

@Service
public class ControlServiceImpl implements ControlService {

	@Autowired 
	private TyCurrencyDao currencyDao; 
	
	@Override
	public ControlLookupsDto getControlLookups() {
		return new ControlLookupsDto(currencyDao.list());
	}

}
