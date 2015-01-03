package com.tucs.business.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import com.tucs.business.dao.interfaces.BaseDao;
import com.tucs.core.commons.log.ILogger;
import com.tucs.core.commons.log.LogManager;
import com.tucs.core.model.entity.BaseModel;

public class AbstractBaseDao<T extends BaseModel> implements BaseDao<T>{
	protected final static ILogger LOGGER = LogManager.getStaticLogger(AbstractBaseDao.class);

	private final Class<T> klass;
	
	@PersistenceContext(unitName="myPersistenceUnit")
    protected EntityManager entityManager;

	public AbstractBaseDao(final Class<T> klass) {
		super();
		this.klass = klass;		
	}
	
	public Class<T> getKlass() {
		return klass;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public T save(T entity) {
		getEntityManager().persist(entity);
		return entity;
	}
	
	public T update(T entity) {
		getEntityManager().merge(entity);
		return entity;
	}
	
	public T saveOrUpdate(T entity) {
		if (entity.getIdReflection() != null) {
			save(entity);			
		} else {
			update(entity);			
		}
		return entity;
	}
	
	public T get(Double id) {
		return getEntityManager().find(klass, id);
	}
	
	public void delete(T entity) {
		getEntityManager().remove(entity);
	}
	
	public List<T> list() {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(klass);
		TypedQuery<T> typedQuery = getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}
}
