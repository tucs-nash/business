package com.tucs.business.dao;

import org.springframework.stereotype.Repository;

import com.tucs.business.dao.interfaces.TyLanguageDao;
import com.tucs.core.model.entity.TyLanguage;

@Repository
public class TyLanguageDaoImpl  extends AbstractBaseDao<TyLanguage> implements TyLanguageDao {

	public TyLanguageDaoImpl() {
		super(TyLanguage.class);
	}

}
