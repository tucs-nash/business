package com.tucs.business.services.interfaces;

import javax.transaction.Transactional;

import com.tucs.core.commons.dto.CategoryLookupsDto;
import com.tucs.core.model.entity.EnCategory;
import com.tucs.core.model.entity.EnUser;



public interface CategoryService extends BaseService{

	public EnCategory getCategory(String categoryId);
	public CategoryLookupsDto getCategoryLookup(String controlId, String categoryId);

	@Transactional
	public EnCategory createCategory(EnCategory enCategory, EnUser user);
	@Transactional
	public EnCategory updateCategory(EnCategory enCategory, EnUser user);


}
