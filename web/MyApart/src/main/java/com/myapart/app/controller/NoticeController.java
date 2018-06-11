package com.myapart.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.myapart.app.model.Notice;
import com.myapart.app.service.NoticeService;
import com.myapart.app.util.ViewPage;

@Controller
public class NoticeController {

	@Autowired
	NoticeService noticeService;
	
	@RequestMapping(value="/notice/registerPage")
	public String noticeRegisterPage() {
		return ViewPage.noticeRegisterPage;
		//return "/notice/register";
	}
	
	@RequestMapping(value="/notice/list", method=RequestMethod.GET)
	public String noticeList(Model model,
			@RequestParam(value="go", required=false, defaultValue="1") String go,
			@RequestParam(value="gogroup", required=false, defaultValue="1") String gogroup,
			@RequestParam(value="keyword", required=false, defaultValue="") String keyword) {
		
		model.addAttribute("noticeList", noticeService.selectNoticeList(keyword));
		model.addAttribute("go", go);
		model.addAttribute("gogroup", gogroup);
		model.addAttribute("keyword", keyword);
		
		return ViewPage.noticeListPage;
		//return "/notice/list";
	}
	
	@RequestMapping(value="/notice/register", method=RequestMethod.POST)
	public String insertNotice(Notice notice) {
		
		int insertResult = noticeService.insertNotice(notice);
		if(insertResult < 1) {
			return ViewPage.reNoticeRegister;
			//return "redirect:/notice/registerPage";
		}
		return ViewPage.reNoticeList;
		//return "redirect:/notice/list";
	}
	
	@RequestMapping(value="/notice/contents", method=RequestMethod.GET)
	public String noticeContents(Model model, 
			@RequestParam(value="no", required=true) int no,
			@RequestParam(value="action", required=false, defaultValue="") String action) {
		
		model.addAttribute("notice", noticeService.selectNoticeOne(no));
		
		if(action.equals("fail") || action.equals("update")) {	
			return ViewPage.noticeUpdatePage;
			//return "/notice/update";
		}
		return ViewPage.noticeContentsPage;
		//return "/notice/contents";
	}
	
	@RequestMapping(value="/notice/update", method=RequestMethod.POST)
	public String noticeUpdate(Notice notice) {
		int updateResult = noticeService.updateNotice(notice);
		if(updateResult < 1) {
			return ViewPage.reNoticeContents +"?no="+notice.getNotNum() + "&action=fail";
			//return "redirect:/notice/contents?no="+notice.getNotNum() + "&action=fail";
		}
		return ViewPage.reNoticeContents + "?no="+notice.getNotNum();
		//return "redirect:/notice/contents?no="+notice.getNotNum();
	}
	
	@RequestMapping(value="/notice/delete", method=RequestMethod.GET)
	public String noticeDelete(@RequestParam("no") int no) {
		int deleteResult = noticeService.deleteNotice(no);
		if(deleteResult < 1) {
			return ViewPage.reNoticeContents + "?no=" + no;
			//return "redirect:/notice/contents?no=" + no;
		}
		return ViewPage.reNoticeList;
		//return "redirect:/notice/list";
	}
	
}
