package com.myapart.app.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.myapart.app.model.Member;
import com.myapart.app.model.Survey;
import com.myapart.app.model.SurveyAnswer;
import com.myapart.app.model.SurveyInfo;
import com.myapart.app.model.SurveyQuestion;
import com.myapart.app.service.SurveyChartService;
import com.myapart.app.service.SurveyService;
import com.myapart.app.util.ViewPage;

@Controller
public class SurveyController {

	@Autowired
	SurveyService surveyService;
	
	@Autowired
	SurveyChartService chartService;
	
	@RequestMapping(value="/survey/registerPage")
	public String surveyRegisterPage() {
		return ViewPage.surveyRegisterPage;
		//return "/survey/adminRegister";
	}
	
	@RequestMapping(value="/survey/list", method=RequestMethod.GET)
	public String surveyList(Model model, HttpSession session,
			@RequestParam(value="mode", required=false, defaultValue="list") String mode,
			@RequestParam(value="go", required=false, defaultValue="1") String go,
			@RequestParam(value="gogroup", required=false, defaultValue="1") String gogroup,
			@RequestParam(value="keyword", required=false, defaultValue="") String keyword) {
		
		model.addAttribute("go", go);
		model.addAttribute("gogroup", gogroup);
		model.addAttribute("keyword", keyword);
		model.addAttribute("mode", mode);

		Member member = (Member)session.getAttribute("loginMember");
		if(member == null) {
			return ViewPage.reHome;
			//return "redirect:/home";
		}
		if((member.getGrade()).equals("admin")) {
			if(mode.equals("parti")) {
				model.addAttribute("surveyList", surveyService.selectSignedSurveyList(keyword));
			}
			else {
				model.addAttribute("surveyList", surveyService.selectSurveyList(keyword));
			}
			return ViewPage.surveyAdminListPage;
			//return "/survey/adminList";
		}
		else {
			if(mode.equals("noparti") || mode.equals("list")) {
				model.addAttribute("surveyList", surveyService.selectSurveyNonePartiList(member.getId(), keyword));
			}
			else {
				model.addAttribute("surveyList", surveyService.selectSurveyPartiList(member.getId(), keyword));
			}
			return ViewPage.surveyListPage;
			//return "/survey/list";
		}
	}
	
	@RequestMapping(value="/survey/register", method=RequestMethod.POST)
	public String surveyRegister(SurveyInfo si, SurveyQuestion sq) {
		
		int insertResult = surveyService.insertSurvey(si, sq);
		if(insertResult < 1) {
			return ViewPage.reSurveyRegister;
			//return "redirect:/survey/registerPage";
		}
		return ViewPage.reSurveyList;
		//return "redirect:/survey/list";
	}
	
	@RequestMapping(value="/survey/contents", method=RequestMethod.GET)
	public String surveyQuestionnarie(Model model, 
			@RequestParam("no") int no,
			@RequestParam(value="action", required=false, defaultValue="") String action) {
		
		Survey survey = surveyService.selectSurveyOne(no);
		
		if(survey == null) {
			return ViewPage.reSurveyList;
			//return "redirect:/survey/list";
		}
		model.addAttribute("survey", survey);		
		if(action.equals("update") || action.equals("fail")) {
			return ViewPage.surveyUpdatePage;
			//return "/survey/adminUpdate";
		}
		return ViewPage.surveyQuestionnairePage;
		//return "/survey/questionnarie";
	}
	@RequestMapping(value="/survey/answerRegister")
	public String surveyAnswerRegister(SurveyInfo si, SurveyAnswer sa) {
		int insertResult = surveyService.insertSurveyAnswer(si, sa);
		if(insertResult < 1) {
			return ViewPage.reSurveyQuestionnarie + "?no="+si.getSurNum();
			//return "redirect:/survey/contents?no="+si.getSurNum();
		}
		return ViewPage.reSurveyList;
		//return "redirect:/survey/list";
	}
	@RequestMapping(value="/survey/answer", method=RequestMethod.POST)
	public String surveyAnswerContents(Model model, SurveyInfo si) {
		Survey survey = surveyService.selectSurveyAnswer(si);
		
		if(survey == null) {
			return ViewPage.reSurveyAdminList;
			//return "redirect:/survey/adminList";
		}
		model.addAttribute("surveyAnswer", survey);
		return ViewPage.surveyAnswerPage;
		//return "/survey/answer";
	}
	
	@RequestMapping(value="/survey/update", method=RequestMethod.POST)
	public String updateSurvey(SurveyInfo si, SurveyQuestion sq) {
		
		
		int updateResult = surveyService.updateSurvey(si, sq);
		
		if(updateResult < 1) {
			return ViewPage.reSurveyQuestionnarie + "?no="+si.getSurNum();
			//return "redirect:/survey/contents?no="+si.getSurNum() +"&action=fail";
		}
		return ViewPage.reSurveyList;
		//return "redirect:/survey/list";
	}
	
	@RequestMapping(value="/survey/delete", method=RequestMethod.GET)
	public String deleteSurvey(@RequestParam("no") int no) {
		
		int deleteResult = surveyService.deleteSurvey(no);
		
		if(deleteResult < 1) {
			return ViewPage.reSurveyQuestionnarie + "?no="+no;
			//return "redirect:/survey/contents?no="+no;
		}
		return ViewPage.reSurveyList;
		//return "redirect:/survey/list";
	}
	
	//-------------------------------------------------------------------------------------
	
	@RequestMapping(value="/survey/chartList", method=RequestMethod.GET)
	public String surveyChartList(Model model,
			@RequestParam(value="go", required=false, defaultValue="1") String go,
			@RequestParam(value="gogroup", required=false, defaultValue="1") String gogroup,
			@RequestParam(value="keyword", required=false, defaultValue="") String keyword) {
		
		model.addAttribute("go", go);
		model.addAttribute("gogroup", gogroup);
		model.addAttribute("keyword", keyword);
		
		model.addAttribute("surveyList", surveyService.selectSurveyList(keyword));
		
		return ViewPage.chartListPage;
		//return "/survey/chartList";
	}
	
	@RequestMapping(value="/survey/chart", method=RequestMethod.GET)
	public String surveyChart(Model model, 
			@RequestParam(value="no", required=true) int no,
			@RequestParam(value="mode", required=false, defaultValue="participation") String mode) {
		
		model.addAttribute("no", no);
		model.addAttribute("mode", mode);
		
		if(mode.equals("participation")) {
			model.addAttribute("participationChart", chartService.selectMemPartiCount(no));
		}
		else if(mode.equals("age")) {
			model.addAttribute("ageChart", chartService.selectAgePartiList(no));
		}
		else {
			model.addAttribute("genderChart", chartService.selectGenderPartiCount(no));
		}
		return ViewPage.chartPage;
		//return "/survey/chart";
	}
}
