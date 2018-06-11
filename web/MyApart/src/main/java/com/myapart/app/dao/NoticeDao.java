package com.myapart.app.dao;

import java.util.List;

import com.myapart.app.model.Notice;

public interface NoticeDao {
	List<Notice> selectNoticeList(String keyword);
	
	int insertNotice(Notice notice);
	
	Notice selectNoticeOne(int no);
	int updateNoticeCount(Notice notice);
	
	int updateNotice(Notice notice);
	
	int deleteNotice(int no);

}
