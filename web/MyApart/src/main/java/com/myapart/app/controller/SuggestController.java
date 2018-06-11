package com.myapart.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.myapart.app.model.Suggest;
import com.myapart.app.service.SuggestService;
import com.myapart.app.util.ViewPage;

@Controller
public class SuggestController {

	@Autowired
	SuggestService suggestService;
	
	@RequestMapping(value="/suggest/registerPage")
	public String suggestRegisterPage() {
		return ViewPage.suggestRegisterPage;
		//return "/suggest/register";
	}
	
	@RequestMapping(value="/suggest/list", method=RequestMethod.GET)
	public String suggestList(Model model,
			@RequestParam(value="go", required=false, defaultValue="1") String go,
			@RequestParam(value="gogroup", required=false, defaultValue="1") String gogroup,
			@RequestParam(value="keyword", required=false, defaultValue="") String keyword) {
		
		model.addAttribute("suggestList", suggestService.selectSuggestList(keyword));
		model.addAttribute("go", go);
		model.addAttribute("gogroup", gogroup);
		model.addAttribute("keyword", keyword);
		
		return ViewPage.suggestListPage;
		//return "/suggest/list";
	}
	
	@RequestMapping(value="/suggest/register", method=RequestMethod.POST)
	public String insertSuggest(Suggest suggest) {
		
		int insertResult = suggestService.insertSuggest(suggest);
		if(insertResult < 1) {
			return ViewPage.reSuggestRegister;
			//return "redirect:/suggest/registerPage";
		}
		return ViewPage.reSuggestList;
		//return "redirect:/suggest/list";
	}
	
	@RequestMapping(value="/suggest/contents", method=RequestMethod.GET)
	public String suggestContents(Model model, 
			@RequestParam(value="no", required=true) int no,
			@RequestParam(value="action", required=false, defaultValue="") String action) {
		
		model.addAttribute("suggest", suggestService.selectSuggestOne(no));
		
		if(action.equals("fail") || action.equals("update")) {
			return ViewPage.suggestUpdatePage;
			//return "/suggest/update";
		}
		return ViewPage.suggestContentsPage;
		//return "/suggest/contents";
	}
	
	@RequestMapping(value="/suggest/update", method=RequestMethod.POST)
	public String updateSuggest(Suggest suggest) {
		
		int updateResult = suggestService.updateSuggest(suggest);
		if(updateResult < 1) {
			return ViewPage.reSuggestContents + "?no=" + suggest.getSugNum() + "&action=fail";
			//return "redirect:/suggest/contents?no="+suggest.getSugNum() + "&action=fail";
		}
		return ViewPage.reSuggestContents + "?no="+suggest.getSugNum();
		//return "redirect:/suggest/contents?no="+suggest.getSugNum();
	}
	
	@RequestMapping(value="/suggest/delete", method=RequestMethod.GET)
	public String deleteSuggest(@RequestParam("no") int no) {
		int deleteResult = suggestService.deleteSuggest(no);
		if(deleteResult < 1) {
			return ViewPage.reSuggestContents + "?no=" + no;
			//return "redirect:/suggest/contents?no="+no;
		}
		return ViewPage.reSuggestList;
		//return "redirect:/suggest/list";
	}
}
