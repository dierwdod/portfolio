package com.myapart.app.service;

import com.myapart.app.model.SurveyChart;

public interface SurveyChartService {
	SurveyChart selectMemPartiCount(int no);
	
	SurveyChart selectGenderPartiCount(int no);
	
	SurveyChart selectAgePartiList(int no);
}
