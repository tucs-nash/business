package com.tucs.business.dao.interfaces;

import java.util.List;

import com.tucs.core.model.entity.EnControl;
import com.tucs.core.model.entity.EnUser;



public interface EnControlDao extends BaseDao<EnControl>{

	public List<EnControl> listUserControl(EnUser user);

}
