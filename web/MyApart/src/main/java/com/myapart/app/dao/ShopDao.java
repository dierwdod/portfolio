package com.myapart.app.dao;

import java.util.List;

import com.myapart.app.model.Shop;

public interface ShopDao {
	List<Shop> selectShopList(String keyword);
	
	int insertShop(Shop shop);
	
	Shop selectShopOne(int no);
	int updateShopCount(Shop shop);
	
	int updateShop(Shop shop);
	
	int deleteShop(int no);
}
