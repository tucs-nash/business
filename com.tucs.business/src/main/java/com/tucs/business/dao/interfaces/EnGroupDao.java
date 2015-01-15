package com.tucs.business.dao.interfaces;

import java.util.List;

import com.tucs.core.model.entity.EnGroup;


public interface EnGroupDao extends BaseDao<EnGroup> {

	public List<EnGroup> getGroupsControl(String controlId);
	public EnGroup getMainGroupControl(String controlId);
	public EnGroup getGroupWithParticipant(String groupId);
}
