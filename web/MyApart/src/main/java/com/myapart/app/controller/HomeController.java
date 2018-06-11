package com.myapart.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.myapart.app.service.HomeService;
import com.myapart.app.util.ViewPage;

@Controller
public class HomeController {
	
	@Autowired
	HomeService homeService;
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Model model) {
		model.addAttribute("homeNotice",homeService.selectHomeNoticeList());
		model.addAttribute("homeShop",homeService.selectHomeShopList());
		return ViewPage.homePage;
		//return "/home/main";
	}
	
}
