package com.tucs.business.dao;

import com.tucs.business.dao.interfaces.TyLanguageDao;
import com.tucs.core.model.entity.TyLanguage;

public class TyLanguageDaoImpl  extends AbstractBaseDao<TyLanguage> implements TyLanguageDao {

	public TyLanguageDaoImpl() {
		super(TyLanguage.class);
	}

}
