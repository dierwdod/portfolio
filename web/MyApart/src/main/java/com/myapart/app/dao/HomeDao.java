package com.myapart.app.dao;

import java.util.List;

import com.myapart.app.model.Home;

public interface HomeDao {
	List<Home> selectHomeNoticeList();
	List<Home> selectHomeShopList();
}
