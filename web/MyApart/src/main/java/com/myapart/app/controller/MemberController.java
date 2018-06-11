package com.myapart.app.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.myapart.app.model.Member;
import com.myapart.app.service.MemberService;
import com.myapart.app.util.ViewPage;

@Controller
public class MemberController {

	@Autowired
	MemberService memberService;
	
	@RequestMapping(value = "/member/loginPage")
	public String loginPage() {
		return ViewPage.memberLoginPage;
		//return "/member/login";
	}
	@RequestMapping(value = "/member/joinPage")
	public String joinPage() {
		return ViewPage.memberJoinPage;
		//return "/member/join";
	}
	@RequestMapping(value = "/member/idCheckPage")
	public String idCheckPage() {
		return ViewPage.memberIdCheckPage;
		//return "/member/idCheck";
	}
	@RequestMapping(value = "/member/logout")
	public String logoutMember(HttpSession session){
		if(session != null)
			session.invalidate();
		return ViewPage.reHome;
		//return "redirect:/home";
	}
	

	@RequestMapping(value = "/member/exist", method = RequestMethod.POST)
	public String idCheckMember(Model model, 
			@RequestParam("checkId") String userid,
			@RequestParam("usable") boolean usable) {
		boolean usableResult = memberService.checkIDExist(userid);
		model.addAttribute("usable", usableResult);
		model.addAttribute("userid", userid);
		return ViewPage.memberIdCheckPage;
		//return "/member/idCheck";
	}
	
	@RequestMapping(value = "/member/join", method = RequestMethod.POST)
	public String insertMenber(Member member) {
		int insertResult = memberService.insertMember(member);
		if(insertResult < 1) {
			return ViewPage.reMemberJoin;
			//return "redirect:/member/joinPage";
		}
		return ViewPage.reHome;
		//return "redirect:/home";
	}
	
	@RequestMapping(value = "/member/login", method = RequestMethod.POST)
	public String memberLogin(Member member, HttpSession session) {
		
		Member mem = memberService.loginMember(member);
		if(mem != null) {
			session.setAttribute("loginMember", mem);
			return ViewPage.reHome;
			//return "redirect:/home";
		}
		return ViewPage.reMemberLogin;
		//return "redirect:/member/loginPage";
	}
	
	@RequestMapping(value = "/member/updateInfo")
	public String updateMemberInfo(Model model, HttpSession session) {
		if(session == null) {
			return ViewPage.reHome;
			//return "redirect:/home";
		}
		
		Member member = (Member)session.getAttribute("loginMember");
		String id = member.getId();
		
		member = memberService.updateSelectOne(id);		
		model.addAttribute("member", member);
			
		return ViewPage.memberUpdatePage;
		//return "/member/update";
	}
	@RequestMapping(value = "/member/update", method = RequestMethod.POST)
	public String updateMember(Member member) {
		int updateResult = memberService.updateMember(member);
		if(updateResult < 1) {
			return ViewPage.reMemberUpdateInfo;
			//return "redirect:/member/updateInfo";
		}
		return ViewPage.reHome;
		//return "redirect:/home";
	}
	
	@RequestMapping(value = "/member/list", method = RequestMethod.GET)
	public String memberList(Model model,
				@RequestParam(value="go", required=false, defaultValue="1") String go,
				@RequestParam(value="gogroup", required=false, defaultValue="1") String gogroup,
				@RequestParam(value="order", required=false, defaultValue="asc") String order,
				@RequestParam(value="what", required=false, defaultValue="id") String what,
				@RequestParam(value="option", required=false, defaultValue="id") String option,
				@RequestParam(value="keyword", required=false, defaultValue="") String keyword){
		
		model.addAttribute("memberList", memberService.selectMemberList(order, what, option, keyword));
		model.addAttribute("go", go);
		model.addAttribute("gogroup", gogroup);
		model.addAttribute("order", order);
		model.addAttribute("what", what);
		model.addAttribute("option",option);
		model.addAttribute("keyword", keyword);
		
		return ViewPage.memberListPage;
		//return "/member/list";
	}
	
	@RequestMapping(value="/member/delete")
	public String deleteMember(HttpSession session) {
		if(session == null) {
			return ViewPage.reHome;
			//return "redirect:/home";
		}
		Member member = (Member)session.getAttribute("loginMember");
		int deleteResult = memberService.deleteMember(member.getId());
		if(deleteResult < 1) {
			return ViewPage.reHome;
			//return "redirect:/home";
		}
		return ViewPage.reMemberLogout;
		//return "redirect:/member/logout";
		
	}
}
