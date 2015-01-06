package com.tucs.business.services.interfaces;

import java.util.List;

import com.tucs.core.model.entity.EnControl;


public interface IndexService extends BaseService{

	public List<EnControl> verifyInitialControl(String userId);
}
