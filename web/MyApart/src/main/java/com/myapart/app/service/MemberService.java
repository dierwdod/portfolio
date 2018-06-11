package com.myapart.app.service;

import java.util.List;

import com.myapart.app.model.Member;

public interface MemberService {
	boolean checkIDExist(String userid);
	int insertMember(Member member);
	
	Member loginMember(Member member);
	
	Member updateSelectOne(String id);
	int updateMember(Member member);
	
	List<Member> selectMemberList(String order, String what, String option, String keyword);
	
	int deleteMember(String id);
}
