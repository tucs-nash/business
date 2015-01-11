package com.tucs.business.dao.interfaces;

import com.tucs.core.model.entity.EnSavings;


public interface EnSavingsDao extends BaseDao<EnSavings>{

	public EnSavings getSavingByControl(String controlId);
}
