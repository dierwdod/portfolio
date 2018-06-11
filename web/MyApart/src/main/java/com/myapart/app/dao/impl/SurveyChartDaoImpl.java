package com.myapart.app.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myapart.app.dao.SurveyChartDao;
import com.myapart.app.model.SurveyChart;

@Repository("surveyChartDao")
public class SurveyChartDaoImpl implements SurveyChartDao {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public SurveyChart selectMemPartiCount(int no) {
		
		return sqlSession.selectOne("selectMemPartiCount", no);
	}

	@Override
	public SurveyChart selectGenderPartiCount(int no) {
		return sqlSession.selectOne("selectGenderPartiCount", no);
	}

	@Override
	public String selectSurveyTitle(int no) {
		return sqlSession.selectOne("selectSurveyTitle", no);
	}

	@Override
	public List<String> selectAgeList(int no) {
		return sqlSession.selectList("selectAgePartiList", no);
	}
	
	
}
