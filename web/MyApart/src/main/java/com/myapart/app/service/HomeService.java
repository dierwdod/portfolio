package com.myapart.app.service;

import java.util.List;

import com.myapart.app.model.Home;

public interface HomeService {
	List<Home> selectHomeNoticeList();
	List<Home> selectHomeShopList();

}
