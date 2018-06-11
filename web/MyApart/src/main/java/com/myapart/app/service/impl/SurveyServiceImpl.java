package com.myapart.app.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.myapart.app.dao.SurveyDao;
import com.myapart.app.model.Survey;
import com.myapart.app.model.SurveyAnswer;
import com.myapart.app.model.SurveyInfo;
import com.myapart.app.model.SurveyParticipation;
import com.myapart.app.model.SurveyQuestion;
import com.myapart.app.service.SurveyService;

@Service("surveyService")
public class SurveyServiceImpl implements SurveyService {

	@Resource(name="surveyDao")
	SurveyDao surveyDao;

	@Override
	public List<SurveyInfo> selectSurveyList(String keyword) {
		return surveyDao.selectSurveyList(keyword);
	}
	@Override
	public List<SurveyInfo> selectSignedSurveyList(String keyword) {
		return surveyDao.selectSignedSurveyList(keyword);
	}

	@Override
	public List<SurveyInfo> selectSurveyPartiList(String id, String keyword) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("id", id);
		paramMap.put("keyword", keyword);
		return surveyDao.selectSurveyPartiList(paramMap);
	}

	@Override
	public List<SurveyInfo> selectSurveyNonePartiList(String id, String keyword) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("id", id);
		paramMap.put("keyword", keyword);
		return surveyDao.selectSurveyNonePartiList(paramMap);
	}

	@Override
	public int insertSurvey(SurveyInfo si, SurveyQuestion sq) {
		int surveyInsert = surveyDao.insertSurvey(si);
		if(surveyInsert < 1) {
			return 0;
		}
		int surNum = surveyDao.selectSurveyLastNum();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		for(int i = 0 ; i < sq.questSize() ; i++) {
			sq.getList().get(i).setSurNum(surNum);
		}
		paramMap.put("qstList", sq.getList());
			
		return surveyDao.insertSurveyQuestion(paramMap);
	}

	@Override
	public Survey selectSurveyOne(int no) {
		Survey survey = null;
		SurveyInfo si = surveyDao.selectSurveyOne(no);
		List<SurveyQuestion> sqList = surveyDao.selectSurveyQuestionList(no);
		if(si != null && sqList.size() > 0) {
			survey = new Survey();
			survey.setSurNum(si.getSurNum());
			survey.setSurTitle(si.getSurTitle());
			survey.setRegDate(si.getRegDate());
			survey.setEndDate(si.getEndDate());
			survey.setName(si.getName());
			
			for(int i = 0 ; i < sqList.size(); i++) {
				survey.setQuestNum(sqList.get(i).getQuestNum());
				survey.setQuestTitle(sqList.get(i).getQuestTitle());
				survey.setQuestType(sqList.get(i).getQuestType());
			}
		}
		
		return survey;
	}

	@Override
	public int insertSurveyAnswer(SurveyInfo si, SurveyAnswer sa) {
		SurveyParticipation sp = new SurveyParticipation();
		sp.setId(si.getId());
		sp.setSurNum(si.getSurNum());
		
		int partiResult = surveyDao.insertSurveyParticipation(sp);
		
		if(partiResult < 1) {
			return 0;
		}
		
		int partiNum = surveyDao.selectSurveyPartiNum(sp);
		

		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		for(int i = 0 ; i < sa.ansSize(); i++) {
			sa.getList().get(i).setPartiNum(partiNum);
		}
		
		paramMap.put("ansList", sa.getList());
	
		return surveyDao.insertSurveyAnswer(paramMap);
	}
	
	@Override
	public Survey selectSurveyAnswer(SurveyInfo si) {
		SurveyInfo surInfo = surveyDao.selectSurveyOne(si.getSurNum());
		Survey survey = null;
		if(surInfo != null) {
			List<SurveyAnswer> saList = surveyDao.selectSurveyAnswer(si);
			if(saList.size() > 0) {
				survey = new Survey();
				survey.setSurNum(surInfo.getSurNum());
				survey.setSurTitle(surInfo.getSurTitle());
				survey.setRegDate(surInfo.getRegDate());
				survey.setEndDate(surInfo.getEndDate());
				survey.setName(surInfo.getName());
							
				for(int i = 0 ; i < saList.size() ; i++) {
					survey.setPartiDate(saList.get(i).getPartiDate());
					survey.setAnsNum(saList.get(i).getAnsNum());
					survey.setAnsTitle(saList.get(i).getAnsTitle());
					survey.setAnsContents(saList.get(i).getAnsContents());
				}
			}
		}
		return survey;
	}
	@Override
	public int updateSurvey(SurveyInfo si, SurveyQuestion sq) {
		int updateResult = surveyDao.updateSurvey(si);
		if(updateResult < 1) {
			return 0;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("surNum", si.getSurNum());
		paramMap.put("updList", sq.getList());
		return surveyDao.updateSurveyQuestion(paramMap);
	}
	@Override
	public int deleteSurvey(int no) {
		return surveyDao.deleteSurvey(no);
	}

	
	
	
}
