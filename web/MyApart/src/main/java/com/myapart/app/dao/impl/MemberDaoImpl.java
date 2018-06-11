package com.myapart.app.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myapart.app.dao.MemberDao;
import com.myapart.app.model.Member;

@Repository("memberDao")
public class MemberDaoImpl implements MemberDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<Member> selectMemberList(Map<String, String> paramMap) {
		return sqlSession.selectList("selectMemberList", paramMap);
	}
	
	@Override
	public boolean checkAdminExist(String userid) {
		if(sqlSession.selectOne("adminExist", userid) == null) {
			return true;
		}
		return false;
	}
	@Override
	public boolean checkMemberExist(String userid) {
		if(sqlSession.selectOne("memberExist", userid) == null) {
			return true;
		}
		return false;
	}

	@Override
	public int insertMember(Member member) {
		return sqlSession.insert("insertMember", member);
	}
	@Override
	public Member loginAdmin(Member member) {
		return sqlSession.selectOne("selectOneAdmin", member);
	}
	@Override
	public Member loginMember(Member member) {
		return sqlSession.selectOne("selectOneMember", member);
	}
	@Override
	public Member updateSelectOne(String id) {
		return sqlSession.selectOne("updateSelectOne", id);
	}
	@Override
	public int updateMember(Member member) {
		return sqlSession.update("updateMember", member);
	}
	
	@Override
	public int deleteMember(String id) {
		return sqlSession.delete("deleteMember", id);
	}
	
	
	
}
