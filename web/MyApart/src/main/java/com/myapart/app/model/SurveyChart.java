package com.myapart.app.model;

import java.util.ArrayList;
import java.util.List;

public class SurveyChart {
	
	private String surTitle;
	
	private String birth;
	private List<String> birthList = new ArrayList<String>();
	
	private int memberCount;
	private int partiCount;
	
	private int manCount;
	private int womanCount;
	
	public String getSurTitle() {
		return surTitle;
	}
	public void setSurTitle(String surTitle) {
		this.surTitle = surTitle;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}	
	public List<String> getBirthList() {
		return this.birthList;
	}
	public void setBirthList(String birth) {
		this.birthList.add(birth);
	}
	
	public int getMemberCount() {
		return memberCount;
	}
	public void setMemberCount(int memberCount) {
		this.memberCount = memberCount;
	}
	public int getPartiCount() {
		return partiCount;
	}
	public void setPartiCount(int partiCount) {
		this.partiCount = partiCount;
	}
	public int getManCount() {
		return manCount;
	}
	public void setManCount(int manCount) {
		this.manCount = manCount;
	}
	public int getWomanCount() {
		return womanCount;
	}
	public void setWomanCount(int womanCount) {
		this.womanCount = womanCount;
	}
	
	
	
}
