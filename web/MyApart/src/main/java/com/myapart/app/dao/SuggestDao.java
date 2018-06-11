package com.myapart.app.dao;

import java.util.List;

import com.myapart.app.model.Suggest;

public interface SuggestDao {
	List<Suggest> selectSuggestList(String keyword);

	int insertSuggest(Suggest suggest);
	
	Suggest selectSuggestOne(int no);
	int updateSuggestCount(Suggest suggest);
	
	int updateSuggest(Suggest suggest);
	
	int deleteSuggest(int no);

}
