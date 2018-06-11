package com.myapart.app.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myapart.app.dao.HomeDao;
import com.myapart.app.model.Home;

@Repository("homeDao")
public class HomeDaoImpl implements HomeDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<Home> selectHomeNoticeList() {
		return sqlSession.selectList("selectHomeNoticeList");
	}

	@Override
	public List<Home> selectHomeShopList() {
		return sqlSession.selectList("selectHomeShopList");
	}

}
