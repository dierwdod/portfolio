package com.myapart.app.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myapart.app.dao.NoticeDao;
import com.myapart.app.model.Notice;

@Repository("noticeDao")
public class NoticeDaoImpl implements NoticeDao {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<Notice> selectNoticeList(String keyword) {
		return sqlSession.selectList("selectNoticeList", keyword);
	}

	@Override
	public int insertNotice(Notice notice) {
		return sqlSession.insert("insertNotice", notice);
	}

	@Override
	public Notice selectNoticeOne(int no) {
		return sqlSession.selectOne("selectNoticeOne", no);
	}

	@Override
	public int updateNoticeCount(Notice notice) {
		return sqlSession.update("updateNoticeCount", notice);
	}

	@Override
	public int updateNotice(Notice notice) {
		return sqlSession.update("updateNotice", notice);
	}

	@Override
	public int deleteNotice(int no) {
		return sqlSession.delete("deleteNotice", no);
	}
}
