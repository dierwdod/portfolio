package com.myapart.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.myapart.app.model.Shop;
import com.myapart.app.service.ShopService;
import com.myapart.app.util.FileUtil;
import com.myapart.app.util.ViewPage;

@Controller
public class ShopController {

	@Autowired
	ShopService shopService;
	
	@RequestMapping(value="/shop/registerPage")
	public String shopRegisterPage() {
		return ViewPage.shopRegisterPage;
		//return "/shop/register";
	}
	
	@RequestMapping(value="/shop/list")
	public String shopList(Model model,
			@RequestParam(value="go", required=false, defaultValue="1") String go,
			@RequestParam(value="gogroup", required=false, defaultValue="1") String gogroup,
			@RequestParam(value="keyword", required=false, defaultValue="") String keyword) {
		
		model.addAttribute("shopList", shopService.selectShopList(keyword));
		model.addAttribute("go", go);
		model.addAttribute("gogroup", gogroup);
		model.addAttribute("keyword", keyword);
		
		return ViewPage.shopListPage;
		//return "/shop/list";
	}
	
	@RequestMapping(value="/shop/register")
	public String insertShop(MultipartHttpServletRequest request) {
		
		Shop shop = new Shop();
		shop.setShopTitle(request.getParameter("shopTitle"));
		shop.setShopContents(request.getParameter("shopContents"));
		shop.setId(request.getParameter("id"));
		
		MultipartFile uploadFile = request.getFile("uploadFile");
		FileUtil fileUtil = FileUtil.getInstance();
				
		boolean uploadResult = fileUtil.uploadFileToServer(uploadFile, shop.getId());
		
		if(!uploadResult) {
			return ViewPage.reShopRegister;
			//return "redirect:/shop/registerPage";
		}
		
		shop.setShopUrl(fileUtil.getFileName());
		int insertResult = shopService.insertShop(shop);
		if(insertResult < 1) {
			return ViewPage.reShopRegister;
			//return "redirect:/shop/registerPage";
		}
		return ViewPage.reShopList;
		//return "redirect:/shop/list";
		
	}
	
	@RequestMapping(value="/shop/contents")
	public String shopContents(Model model,
			@RequestParam(value="no", required=true) int no,
			@RequestParam(value="action", required=false, defaultValue="") String action) {
		
		model.addAttribute("shop", shopService.selectShopOne(no));
		
		if(action.equals("fail") || action.equals("update")) {
			return ViewPage.shopUpdatePage;
			//return "/shop/update";
		}
		return ViewPage.shopContentsPage;
		//return "/shop/contents";
	}
	@RequestMapping(value="/shop/update", method=RequestMethod.POST)
	public String noticeUpdate(MultipartHttpServletRequest request) {

		Shop shop = new Shop();
		shop.setShopNum(Integer.parseInt(request.getParameter("shopNum")));
		shop.setShopTitle(request.getParameter("shopTitle"));
		shop.setShopContents(request.getParameter("shopContents"));
		shop.setId(request.getParameter("id"));
		
		MultipartFile uploadFile = request.getFile("uploadFile");
		FileUtil fileUtil = FileUtil.getInstance();

		boolean uploadResult = fileUtil.uploadFileToServer(uploadFile, shop.getId());
		
		if(!uploadResult) {
			return ViewPage.reShopContents + "?no="+shop.getShopNum() + "&action=fail";
			//return "redirect:/shop/contents?no="+shop.getShopNum()+"&action=fail";
		}
		shop.setShopUrl(fileUtil.getFileName());
		
		int updateResult = shopService.updateShop(shop);
		if(updateResult < 1) {
			return ViewPage.reShopContents + "?no="+shop.getShopNum();
			//return "redirect:/shop/contents?no="+shop.getShopNum()+"&action=fail";
		}
		return ViewPage.reShopContents + "?no="+shop.getShopNum();
		//return "redirect:/shop/contents?no="+shop.getShopNum();
	}
	
	@RequestMapping(value="/shop/delete", method=RequestMethod.GET)
	public String noticeDelete(@RequestParam("no") int no) {
		Shop shop = shopService.selectShopOne(no);
		
		int deleteResult = shopService.deleteShop(no);
		if(deleteResult < 1) {
			return ViewPage.reShopContents + "?no=" + no;
			//return "redirect:/shop/contents?no="+no;
		}
		
		FileUtil fileUtil = FileUtil.getInstance();
		boolean deleteFileResult = fileUtil.deleteUploadFile(shop.getShopUrl());
		if(!deleteFileResult) {
			return ViewPage.reShopContents + "?no=" + no;
			//return "redirect:/shop/contents?no="+no;
		}
		return ViewPage.reShopList;
		//return "redirect:/shop/list";
	}
}
