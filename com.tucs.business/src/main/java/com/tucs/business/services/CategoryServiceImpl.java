package com.tucs.business.services;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tucs.business.dao.interfaces.EnCategoryDao;
import com.tucs.business.dao.interfaces.EnControlDao;
import com.tucs.business.dao.interfaces.EnTransactionDao;
import com.tucs.business.services.interfaces.CategoryService;
import com.tucs.core.commons.dto.CategoryLookupsDto;
import com.tucs.core.model.entity.EnCategory;
import com.tucs.core.model.entity.EnControl;
import com.tucs.core.model.entity.EnUser;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired EnCategoryDao categoryDao;
	@Autowired EnControlDao controlDao;
	@Autowired EnTransactionDao transactionDao;

	@Override
	public EnCategory getCategory(String categoryId) {
		return categoryDao.get(categoryId);
	}

	@Override
	public EnCategory createCategory(EnCategory enCategory, EnUser user) {
		if (enCategory.getBudget() == null) {
			enCategory.setBudget(0d);
		}
		
		enCategory.setCreatedUser(user);
		enCategory.setCreatedDate(LocalDateTime.now());
		return categoryDao.save(enCategory);
	}

	@Override
	public EnCategory updateCategory(EnCategory enCategory, EnUser user) {
		enCategory.setUpdatedUser(user);
		enCategory.setUpdatedDate(LocalDateTime.now());
		return categoryDao.update(enCategory);
	}
	
	@Override
	public CategoryLookupsDto getCategoryLookup(String controlId, String categoryId) {
		CategoryLookupsDto lookupsDto = new CategoryLookupsDto();
		
		EnControl control = controlDao.get(controlId);
		lookupsDto.setHasClosing(control.getHasClosing());
		lookupsDto.setNameControl(control.getName());
		
		lookupsDto.setCategories(categoryDao.getCategoriesByControl(controlId, categoryId));
		return lookupsDto;
	}
}
