package com.myapart.app.model;

import java.util.List;

public class SurveyAnswer {
	
	private int ansNum;
	private String ansTitle;
	private String ansContents;
	private String ansType;
	private int partiNum;
	private String partiDate;
	
	private List<SurveyAnswer> list;
	
	public int getAnsNum() {
		return ansNum;
	}
	public void setAnsNum(int ansNum) {
		this.ansNum = ansNum;
	}
	public String getAnsTitle() {
		return ansTitle;
	}
	public void setAnsTitle(String ansTitle) {
		this.ansTitle = ansTitle;
	}
	public String getAnsContents() {
		return ansContents;
	}
	public void setAnsContents(String ansContents) {
		this.ansContents = ansContents;
	}
	public String getAnsType() {
		return ansType;
	}
	public void setAnsType(String ansType) {
		this.ansType = ansType;
	}
	public String getPartiDate() {
		return partiDate;
	}
	public void setPartiDate(String partiDate) {
		this.partiDate = partiDate;
	}
	public int getPartiNum() {
		return partiNum;
	}
	public void setPartiNum(int partiNum) {
		this.partiNum = partiNum;
	}
	public List<SurveyAnswer> getList() {
		return list;
	}
	public void setList(List<SurveyAnswer> list) {
		this.list = list;
	}
	
	public int ansSize() {
		return this.list.size();
	}
	
	
}	
