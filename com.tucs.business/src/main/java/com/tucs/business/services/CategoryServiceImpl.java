package com.tucs.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tucs.business.dao.interfaces.EnCategoryDao;
import com.tucs.business.services.interfaces.CategoryService;
import com.tucs.core.model.entity.EnCategory;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired EnCategoryDao categoryDao;

	@Override
	public EnCategory getCategory(String categoryId) {
		return categoryDao.get(categoryId);
	}

	@Override
	public EnCategory createCategory(EnCategory enCategory) {
		return categoryDao.save(enCategory);
	}

	@Override
	public EnCategory updateCategory(EnCategory enCategory) {
		return categoryDao.update(enCategory);
	}
	
}
