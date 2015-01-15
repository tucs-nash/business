package com.tucs.business.dao.interfaces;

import java.util.List;

import com.tucs.core.model.entity.TyLanguage;


public interface TyLanguageDao extends BaseDao<TyLanguage>{

	public List<TyLanguage> listLanguagueOrderByName();
	
	

}
