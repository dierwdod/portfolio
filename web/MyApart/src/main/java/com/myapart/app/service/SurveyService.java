package com.myapart.app.service;

import java.util.List;

import com.myapart.app.model.Survey;
import com.myapart.app.model.SurveyAnswer;
import com.myapart.app.model.SurveyInfo;
import com.myapart.app.model.SurveyQuestion;

public interface SurveyService {
	List<SurveyInfo> selectSurveyList(String keyword);
	List<SurveyInfo> selectSignedSurveyList(String keyword);

	List<SurveyInfo> selectSurveyPartiList(String id, String keyword);
	List<SurveyInfo> selectSurveyNonePartiList(String id, String keyword);
	
	int insertSurvey(SurveyInfo si, SurveyQuestion sq);
	
	Survey selectSurveyOne(int no);
	
	int insertSurveyAnswer(SurveyInfo si, SurveyAnswer sa);
	
	Survey selectSurveyAnswer(SurveyInfo si);
	
	int updateSurvey(SurveyInfo si, SurveyQuestion sq);
	
	int deleteSurvey(int no);
}
