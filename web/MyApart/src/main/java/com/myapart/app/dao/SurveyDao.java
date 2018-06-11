package com.myapart.app.dao;

import java.util.List;
import java.util.Map;

import com.myapart.app.model.SurveyAnswer;
import com.myapart.app.model.SurveyInfo;
import com.myapart.app.model.SurveyParticipation;
import com.myapart.app.model.SurveyQuestion;

public interface SurveyDao {
	List<SurveyInfo> selectSurveyList(String keyword);
	List<SurveyInfo> selectSignedSurveyList(String keyword);
	
	List<SurveyInfo> selectSurveyPartiList(Map<String, String> paramMap);
	List<SurveyInfo> selectSurveyNonePartiList(Map<String, String> paramMap);
	
	int insertSurvey(SurveyInfo si);
	int insertSurveyQuestion(Map<String, Object> paramMap);
	
	SurveyInfo selectSurveyOne(int no);
	List<SurveyQuestion> selectSurveyQuestionList(int no);
	
	int insertSurveyParticipation(SurveyParticipation sp);
	int selectSurveyPartiNum(SurveyParticipation sp);
	int insertSurveyAnswer(Map<String, Object> paramMap);
	
	List<SurveyAnswer> selectSurveyAnswer(SurveyInfo si);
	
	int updateSurvey(SurveyInfo si);
	int updateSurveyQuestion(Map<String, Object> paramMap);
	
	int deleteSurvey(int no);
	
	int selectSurveyLastNum();
}
