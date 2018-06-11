package com.myapart.app.service;

import java.util.List;

import com.myapart.app.model.Suggest;

public interface SuggestService {
	List<Suggest> selectSuggestList(String keyword);
	
	int insertSuggest(Suggest suggest);
	
	Suggest selectSuggestOne(int no);
	
	int updateSuggest(Suggest suggest);

	int deleteSuggest(int no);
}
