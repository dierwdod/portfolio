package com.myapart.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.myapart.app.dao.ShopDao;
import com.myapart.app.model.Shop;
import com.myapart.app.service.ShopService;

@Service("shopService")
public class ShopServiceImpl implements ShopService {

	@Resource(name="shopDao")
	ShopDao shopDao;

	@Override
	public List<Shop> selectShopList(String keyword) {
		return shopDao.selectShopList(keyword);
	}

	@Override
	public int insertShop(Shop shop) {
		return shopDao.insertShop(shop);
	}

	@Override
	public Shop selectShopOne(int no) {
		Shop shop = shopDao.selectShopOne(no);
		System.out.println("---------------Shop ( Select One )---------------");
		System.out.println(shop.getShopNum() + ", " + shop.getShopTitle() +" , " + shop.getShopContents() + ", " + shop.getShopDate() + ", " + shop.getShopCount() +", " + shop.getId());
		shop.setShopCount(shop.getShopCount()+1);
		int countResult = shopDao.updateShopCount(shop);
		return shop;
	}

	@Override
	public int updateShop(Shop shop) {
		return shopDao.updateShop(shop);
	}

	@Override
	public int deleteShop(int no) {
		return shopDao.deleteShop(no);
	}
	
	
	
	
}
