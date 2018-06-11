package com.myapart.app.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myapart.app.dao.ShopDao;
import com.myapart.app.model.Shop;

@Repository("shopDao")
public class ShopDaoImpl implements ShopDao {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<Shop> selectShopList(String keyword) {
		return sqlSession.selectList("selectShopList",keyword);
	}

	@Override
	public int insertShop(Shop shop) {
		return sqlSession.insert("insertShop", shop);
	}

	@Override
	public Shop selectShopOne(int no) {
		return sqlSession.selectOne("selectShopOne", no);
	}

	@Override
	public int updateShopCount(Shop shop) {
		return sqlSession.update("updateShopCount", shop);
	}

	@Override
	public int updateShop(Shop shop) {
		return sqlSession.update("updateShop",shop);
	}

	@Override
	public int deleteShop(int no) {
		return sqlSession.delete("deleteShop",no);
	}
}
