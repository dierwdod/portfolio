package com.myapart.app.dao;

import java.util.List;

import com.myapart.app.model.SurveyChart;

public interface SurveyChartDao {
	SurveyChart selectMemPartiCount(int no);
	
	SurveyChart selectGenderPartiCount(int no);

	String selectSurveyTitle(int no);
	List<String> selectAgeList(int no);
}
