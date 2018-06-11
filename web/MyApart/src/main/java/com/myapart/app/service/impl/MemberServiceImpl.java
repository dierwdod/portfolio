package com.myapart.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.myapart.app.dao.MemberDao;
import com.myapart.app.model.Member;
import com.myapart.app.service.MemberService;

@Service("memberService")
public class MemberServiceImpl implements MemberService{

	@Resource(name="memberDao")
	private MemberDao memberDao;
	
	
	@Override
	public boolean checkIDExist(String userid) {
		boolean adminCheck = memberDao.checkAdminExist(userid);
		boolean memberCheck = memberDao.checkMemberExist(userid);
		if(!adminCheck || !memberCheck) {
			return false;
		}
		return true;
	}

	@Override
	public int insertMember(Member member) {
		return memberDao.insertMember(member);
	}


	@Override
	public Member loginMember(Member member) {
		Member mem = null;
		if(memberDao.loginAdmin(member) != null) {
			mem = memberDao.loginAdmin(member);
		}
		else {
			if(memberDao.loginMember(member) != null) {
				mem = memberDao.loginMember(member);
			}
		}
		return mem;
	}


	@Override
	public Member updateSelectOne(String id) {
		return memberDao.updateSelectOne(id);
	}


	@Override
	public int updateMember(Member member) {
		return memberDao.updateMember(member);
	}

	@Override
	public List<Member> selectMemberList(String order, String what, String option, String keyword) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("order", order);
		paramMap.put("what", what);
		paramMap.put("option", option);
		paramMap.put("keyword", keyword);
		
		return memberDao.selectMemberList(paramMap);
	}


	@Override
	public int deleteMember(String id) {
		return memberDao.deleteMember(id);
	}
	
	
	
}
