package com.tucs.business.dao.interfaces;

import java.util.List;

import com.tucs.core.model.entity.EnCategory;


public interface EnCategoryDao extends BaseDao<EnCategory>{

	public List<EnCategory> getCategoriesByControl(String controlId);
}
