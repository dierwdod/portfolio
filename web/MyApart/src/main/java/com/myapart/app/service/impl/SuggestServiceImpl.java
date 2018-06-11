package com.myapart.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.myapart.app.dao.SuggestDao;
import com.myapart.app.model.Suggest;
import com.myapart.app.service.SuggestService;

@Service("suggestService")
public class SuggestServiceImpl implements SuggestService {

	@Resource(name="suggestDao")
	SuggestDao suggestDao;

	@Override
	public List<Suggest> selectSuggestList(String keyword) {
		return suggestDao.selectSuggestList(keyword);
	}

	@Override
	public int insertSuggest(Suggest suggest) {
		return suggestDao.insertSuggest(suggest);
	}

	@Override
	public Suggest selectSuggestOne(int no) {
		Suggest suggest = suggestDao.selectSuggestOne(no);
		suggest.setSugCount(suggest.getSugCount()+1);
		int updateCount = suggestDao.updateSuggestCount(suggest);
		
		return suggest;
	}

	@Override
	public int updateSuggest(Suggest suggest) {
		return suggestDao.updateSuggest(suggest);
	}

	@Override
	public int deleteSuggest(int no) {
		return suggestDao.deleteSuggest(no);
	}
	
	
}
