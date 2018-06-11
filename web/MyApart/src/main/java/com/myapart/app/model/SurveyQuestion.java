package com.myapart.app.model;

import java.util.List;

public class SurveyQuestion {
	
	private int questNum;
	private String questTitle;
	private String questType;
	private int surNum;
	
	private List<SurveyQuestion> list;

	public int getQuestNum() {
		return questNum;
	}
	public void setQuestNum(int questNum) {
		this.questNum = questNum;
	}
	public String getQuestTitle() {
		return questTitle;
	}
	public void setQuestTitle(String questTitle) {
		this.questTitle = questTitle;
	}
	public String getQuestType() {
		return questType;
	}
	public void setQuestType(String questType) {
		this.questType = questType;
	}
	public int getSurNum() {
		return surNum;
	}
	public void setSurNum(int surNum) {
		this.surNum = surNum;
	}
	
	public List<SurveyQuestion> getList() {
		return list;
	}
	public void setList(List<SurveyQuestion> list) {
		this.list = list;
	}
	
	public int questSize() {
		return list.size();
	}
	
}
