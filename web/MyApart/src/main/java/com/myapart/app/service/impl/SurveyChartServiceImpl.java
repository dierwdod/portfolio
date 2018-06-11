package com.myapart.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.myapart.app.dao.SurveyChartDao;
import com.myapart.app.model.SurveyChart;
import com.myapart.app.service.SurveyChartService;

@Service("surveyChartService")
public class SurveyChartServiceImpl implements SurveyChartService {

	@Resource(name="surveyChartDao")
	SurveyChartDao chartDao;

	@Override
	public SurveyChart selectMemPartiCount(int no) {
		return chartDao.selectMemPartiCount(no);
	}

	@Override
	public SurveyChart selectGenderPartiCount(int no) {
		return chartDao.selectGenderPartiCount(no);
	}

	@Override
	public SurveyChart selectAgePartiList(int no) {
		SurveyChart sc = null;
		String title = chartDao.selectSurveyTitle(no);
		if(title != null) {
			sc = new SurveyChart();
			sc.setSurTitle(title);
			List<String> list = chartDao.selectAgeList(no);
			for(int i = 0 ; i < list.size(); i++) {
				sc.setBirthList(list.get(i));
			}
		}
		
		return sc;
	}
	
	
}
