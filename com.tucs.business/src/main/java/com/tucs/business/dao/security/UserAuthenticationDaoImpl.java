package com.tucs.business.dao.security;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.tucs.business.dao.AbstractBaseDao;
import com.tucs.business.dao.security.interfaces.UserAuthenticationDao;
import com.tucs.core.model.entity.EnUser;

public class UserAuthenticationDaoImpl extends AbstractBaseDao<EnUser> implements UserAuthenticationDao {

	public UserAuthenticationDaoImpl() {
		super(EnUser.class);
	}

	@Override
	public EnUser getUserByLogin(String email) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<EnUser> criteriaQuery = criteriaBuilder.createQuery(EnUser.class);
		Root<EnUser> from = criteriaQuery.from(EnUser.class);
		
		criteriaQuery.where(criteriaBuilder.equal(from.get("email"), email));
				//, criteriaBuilder.equal(from.get("password"), password));
		TypedQuery<EnUser> typedQuery = getEntityManager().createQuery(criteriaQuery);
		
		try {
			return typedQuery.getSingleResult();			
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Override
	public EnUser getUserByLoginPassword(String email, String password) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<EnUser> criteriaQuery = criteriaBuilder.createQuery(EnUser.class);
		Root<EnUser> from = criteriaQuery.from(EnUser.class);
		
		criteriaQuery.where(criteriaBuilder.equal(from.get("email"), email), criteriaBuilder.equal(from.get("password"), password));
		TypedQuery<EnUser> typedQuery = getEntityManager().createQuery(criteriaQuery);
		
		try {
			return typedQuery.getSingleResult();			
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Boolean verifyEmail(String email) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);

        Root<EnUser> tableFrom = criteriaQuery.from(EnUser.class);
        criteriaQuery.select(criteriaBuilder.count(tableFrom));
        criteriaQuery.where(criteriaBuilder.equal(tableFrom.get("email"), email));
        
        TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery);
        
        return typedQuery.getSingleResult() > 0;
	}
	
}
