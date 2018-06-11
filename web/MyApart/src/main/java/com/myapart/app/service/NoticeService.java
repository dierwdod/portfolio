package com.myapart.app.service;

import java.util.List;

import com.myapart.app.model.Notice;

public interface NoticeService {
	List<Notice> selectNoticeList(String keyword);
	
	int insertNotice(Notice notice);
	
	Notice selectNoticeOne(int no);
	
	int updateNotice(Notice notice);
	
	int deleteNotice(int no);
}
