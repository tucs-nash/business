package com.tucs.business.dao;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.tucs.business.dao.interfaces.TyLanguageDao;
import com.tucs.core.model.entity.TyLanguage;

@Repository
public class TyLanguageDaoImpl  extends AbstractBaseDao<TyLanguage> implements TyLanguageDao {

	public TyLanguageDaoImpl() {
		super(TyLanguage.class);
	}

	@Override
	public List<TyLanguage> listLanguagueOrderByName() {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<TyLanguage> criteriaQuery = criteriaBuilder.createQuery(TyLanguage.class);
		
		Root<TyLanguage> from = criteriaQuery.from(TyLanguage.class);
		
		criteriaQuery.orderBy(criteriaBuilder.asc(from.get("name")));
		TypedQuery<TyLanguage> typedQuery = getEntityManager().createQuery(criteriaQuery);		
		
		return typedQuery.getResultList();
	}

}
