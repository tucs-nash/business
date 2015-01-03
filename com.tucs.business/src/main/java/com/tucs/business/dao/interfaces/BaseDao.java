package com.tucs.business.dao.interfaces;

import java.util.List;

import com.tucs.core.model.entity.BaseModel;


public interface BaseDao<T extends BaseModel> {
	
	public T save(T entity);
	public T update(T entity);
	public T saveOrUpdate(T entity);
	public T get(Object id);
	public void delete(T entity);
	public List<T> list();
}
