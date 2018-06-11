package com.myapart.app.model;

import java.util.ArrayList;
import java.util.List;

public class Survey {
	private String name;
	private String id;
	
	private int surNum;
	private String surTitle;
	private String regDate;
	private String endDate;
	
	private List<Integer> questNum = new ArrayList<Integer>();
	private List<String> questTitle = new ArrayList<String>();
	private List<String> questType = new ArrayList<String>();
	
	private int partiNum;
	private String partiDate;
	
	private List<Integer> ansNum = new ArrayList<Integer>();
	private List<String> ansTitle = new ArrayList<String>();
	private List<String> ansType = new ArrayList<String>();
	private List<String> ansContents = new ArrayList<String>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getSurNum() {
		return surNum;
	}
	public void setSurNum(int surNum) {
		this.surNum = surNum;
	}
	public String getSurTitle() {
		return surTitle;
	}
	public void setSurTitle(String surTitle) {
		this.surTitle = surTitle;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getQuestNum(int index) {
		return questNum.get(index);
	}
	public void setQuestNum(int questNum) {
		this.questNum.add(questNum);
	}
	public String getQuestTitle(int index) {
		return questTitle.get(index);
	}
	public void setQuestTitle(String questTitle) {
		this.questTitle.add(questTitle);
	}
	public String getQuestType(int index) {
		return questType.get(index);
	}
	public void setQuestType(String questType) {
		this.questType.add(questType);
	}
	public int questSize() {
		return questTitle.size();
	}
	
	public int getPartiNum() {
		return partiNum;
	}
	public void setPartiNum(int partiNum) {
		this.partiNum = partiNum;
	}
	public String getPartiDate() {
		return partiDate;
	}
	public void setPartiDate(String partiDate) {
		this.partiDate = partiDate;
	}
	public int getAnsNum(int index) {
		return ansNum.get(index);
	}
	public void setAnsNum(int ansNum) {
		this.ansNum.add(ansNum);
	}
	public String getAnsTitle(int index) {
		return ansTitle.get(index);
	}
	public void setAnsTitle(String ansTitle) {
		this.ansTitle.add(ansTitle);
	}
	public String getAnsType(int index) {
		return ansType.get(index);
	}
	public void setAnsType(String ansType) {
		this.ansType.add(ansType);
	}
	public String getAnsContents(int index) {
		return ansContents.get(index);
	}
	public void setAnsContents(String ansContents) {
		this.ansContents.add(ansContents);
	}
	public int ansSize() {
		return ansTitle.size();
	}
	
}
