package com.myapart.app.service;

import java.util.List;

import com.myapart.app.model.Shop;

public interface ShopService {
	List<Shop> selectShopList(String keyword);
	
	int insertShop(Shop shop);
	
	Shop selectShopOne(int no);
	
	int updateShop(Shop shop);
	
	int deleteShop(int no);
}
