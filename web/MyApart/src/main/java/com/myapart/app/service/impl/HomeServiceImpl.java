package com.myapart.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.myapart.app.dao.HomeDao;
import com.myapart.app.model.Home;
import com.myapart.app.service.HomeService;

@Service("homeService")
public class HomeServiceImpl implements HomeService {

	@Resource(name="homeDao")
	private HomeDao homeDao;
	
	@Override
	public List<Home> selectHomeNoticeList() {
		return homeDao.selectHomeNoticeList();
	}

	@Override
	public List<Home> selectHomeShopList() {
		return homeDao.selectHomeShopList();
	}

}
