package com.myapart.app.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myapart.app.dao.SuggestDao;
import com.myapart.app.model.Suggest;

@Repository("suggestDao")
public class SuggestDaoImpl implements SuggestDao {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<Suggest> selectSuggestList(String keyword) {
		return sqlSession.selectList("selectSuggestList", keyword);
	}

	@Override
	public int insertSuggest(Suggest suggest) {
		return sqlSession.insert("insertSuggest", suggest);
	}

	@Override
	public Suggest selectSuggestOne(int no) {
		return sqlSession.selectOne("selectSuggestOne", no);
	}

	@Override
	public int updateSuggestCount(Suggest suggest) {
		return sqlSession.update("updateSuggestCount", suggest);
	}

	@Override
	public int updateSuggest(Suggest suggest) {
		return sqlSession.update("updateSuggest", suggest);
	}

	@Override
	public int deleteSuggest(int no) {
		return sqlSession.delete("deleteSuggest", no);
	}
}
