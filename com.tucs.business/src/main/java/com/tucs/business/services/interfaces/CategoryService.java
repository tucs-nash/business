package com.tucs.business.services.interfaces;

import javax.transaction.Transactional;

import com.tucs.core.model.entity.EnCategory;



public interface CategoryService extends BaseService{

	public EnCategory getCategory(String categoryId);

	@Transactional
	public EnCategory createCategory(EnCategory enCategory);
	@Transactional
	public EnCategory updateCategory(EnCategory enCategory);

}
