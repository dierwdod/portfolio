package com.myapart.app.dao;

import java.util.List;
import java.util.Map;

import com.myapart.app.model.Member;

public interface MemberDao {
	boolean checkAdminExist(String userid);
	boolean checkMemberExist(String userid);
	
	int insertMember(Member member);
	
	Member loginAdmin(Member member);
	Member loginMember(Member member);
	
	Member updateSelectOne(String id);
	int updateMember(Member member);
	
	List<Member> selectMemberList(Map<String, String> paramMap);
	
	int deleteMember(String id);

}
