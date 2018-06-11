package com.myapart.app.util;

public interface ViewPage {
	//----------------------home----------------------
	String homePage = "/home/main";
	String reHome = "redirect:/home";

	//----------------------member----------------------
	String memberLoginPage = "/member/login";
	String memberJoinPage = "/member/join";
	String memberIdCheckPage = "/member/idCheck";
	String memberUpdatePage = "/member/update";
	String memberListPage = "/member/list";
	
	String reMemberJoin = "redirect:/member/joinPage";
	String reMemberLogin = "redirect:/member/loginPage";
	String reMemberUpdateInfo = "redirect:/member/updateInfo";
	String reMemberLogout = "redirect:/member/logout";
	
	//----------------------Notice----------------------
	String noticeRegisterPage = "/notice/register";
	String noticeListPage = "/notice/list";
	String noticeUpdatePage = "/notice/update";
	String noticeContentsPage = "/notice/contents";
	
	String reNoticeRegister = "redirect:/notice/registerPage";
	String reNoticeList = "redirect:/notice/list";
	String reNoticeContents = "redirect:/notice/contents";
	
	//----------------------Suggest----------------------
	String suggestRegisterPage = "/suggest/register";
	String suggestListPage = "/suggest/list";
	String suggestUpdatePage = "/suggest/update";
	String suggestContentsPage = "/suggest/contents";
	
	String reSuggestRegister = "redirect:/suggest/registerPage";
	String reSuggestList = "redirect:/suggest/list";
	String reSuggestContents = "redirect:/suggest/contents";
	
	//----------------------Shop----------------------
	String shopRegisterPage = "/shop/register";
	String shopListPage = "/shop/list";
	String shopContentsPage = "/shop/contents";
	String shopUpdatePage = "/shop/update";
	
	String reShopRegister = "redirect:/shop/registerPage";
	String reShopList = "redirect:/shop/list";
	String reShopContents = "redirect:/shop/contents";
	
	//----------------------Survey----------------------
	String surveyRegisterPage = "/survey/adminRegister";
	String surveyAdminListPage = "/survey/adminList";
	String surveyListPage = "/survey/list";
	String surveyUpdatePage = "/survey/adminUpdate";
	String surveyQuestionnairePage = "/survey/questionnarie";
	String surveyAnswerPage = "/survey/answer";
	
	String reSurveyRegister = "redirect:/survey/registerPage";
	String reSurveyList = "redirect:/survey/list";
	String reSurveyAdminList = "redirect:/survey/adminList";
	String reSurveyQuestionnarie = "redirect:/survey/contents";
	
	//-------------------Survey(Chart)-------------------
	String chartListPage = "/survey/chartList";
	String chartPage = "/survey/chart";
	
}	
