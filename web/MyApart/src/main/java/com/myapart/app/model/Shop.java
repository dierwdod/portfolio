package com.myapart.app.model;

import org.springframework.web.multipart.MultipartFile;

public class Shop {
	
	private int shopNum;
	private String shopTitle;
	private String shopContents;
	private String shopUrl;
	private String shopDate;
	private int shopCount;
	private String id;
		
	public int getShopNum() {
		return shopNum;
	}
	public void setShopNum(int shopNum) {
		this.shopNum = shopNum;
	}
	public String getShopTitle() {
		return shopTitle;
	}
	public void setShopTitle(String shopTitle) {
		this.shopTitle = shopTitle;
	}
	public String getShopContents() {
		return shopContents;
	}
	public void setShopContents(String shopContents) {
		this.shopContents = shopContents;
	}
	public String getShopUrl() {
		return shopUrl;
	}
	public void setShopUrl(String shopUrl) {
		this.shopUrl = shopUrl;
	}
	public String getShopDate() {
		return shopDate;
	}
	public void setShopDate(String shopDate) {
		this.shopDate = shopDate;
	}
	public int getShopCount() {
		return shopCount;
	}
	public void setShopCount(int shopCount) {
		this.shopCount = shopCount;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
