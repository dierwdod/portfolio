package com.myapart.app.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myapart.app.dao.SurveyDao;
import com.myapart.app.model.SurveyAnswer;
import com.myapart.app.model.SurveyInfo;
import com.myapart.app.model.SurveyParticipation;
import com.myapart.app.model.SurveyQuestion;

@Repository("surveyDao")
public class SurveyDaoImpl implements SurveyDao {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<SurveyInfo> selectSurveyList(String keyword) {
		return sqlSession.selectList("selectSurveyList", keyword);
	}
	@Override
	public List<SurveyInfo> selectSignedSurveyList(String keyword) {
		return sqlSession.selectList("selectSignedSurveyList", keyword);
	}
	
	@Override
	public List<SurveyInfo> selectSurveyPartiList(Map<String, String> paramMap) {
		return sqlSession.selectList("selectSurveyPartiList", paramMap);
	}
	@Override
	public List<SurveyInfo> selectSurveyNonePartiList(Map<String, String> paramMap) {
		return sqlSession.selectList("selectSurveyNonePartiList", paramMap);
	}

	@Override
	public int insertSurvey(SurveyInfo si) {
		return sqlSession.insert("insertSurvey", si);
	}

	@Override
	public int insertSurveyQuestion(Map<String, Object> paramMap) {
		return sqlSession.insert("insertSurveyQuestion", paramMap);
	}

	@Override
	public SurveyInfo selectSurveyOne(int no) {
		return sqlSession.selectOne("surveyQuestionnarieHeader", no);
	}

	@Override
	public List<SurveyQuestion> selectSurveyQuestionList(int no) {
		return sqlSession.selectList("surveyQuestionnarieContents", no);
	}

	@Override
	public int insertSurveyParticipation(SurveyParticipation sp) {
		return sqlSession.insert("insertParticipation", sp);
	}
	@Override
	public int selectSurveyPartiNum(SurveyParticipation sp) {
		return sqlSession.selectOne("participationNumber", sp);
	}
	@Override
	public int insertSurveyAnswer(Map<String, Object> paramMap) {
		return sqlSession.insert("insertSurveyAnswer", paramMap);
	}

	@Override
	public List<SurveyAnswer> selectSurveyAnswer(SurveyInfo si) {
		return sqlSession.selectList("selectAnswerList", si);
	}
	@Override
	public int updateSurvey(SurveyInfo si) {
		return sqlSession.update("updateSurvey", si);
	}
	@Override
	public int updateSurveyQuestion(Map<String, Object> paramMap) {
		return sqlSession.update("updateSurveyQuestion", paramMap);
	}
	@Override
	public int deleteSurvey(int no) {
		return sqlSession.delete("deleteSurvey", no);
	}
	@Override
	public int selectSurveyLastNum() {
		return sqlSession.selectOne("selectSurveyLastNum");
	}

	
	
}
