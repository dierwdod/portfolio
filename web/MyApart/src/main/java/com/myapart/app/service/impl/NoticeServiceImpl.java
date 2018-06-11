package com.myapart.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.myapart.app.dao.NoticeDao;
import com.myapart.app.model.Notice;
import com.myapart.app.service.NoticeService;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {

	@Resource(name="noticeDao")
	private NoticeDao noticeDao;

	@Override
	public List<Notice> selectNoticeList(String keyword) {
		return noticeDao.selectNoticeList(keyword);
	}

	@Override
	public int insertNotice(Notice notice) {
		return noticeDao.insertNotice(notice);
	}

	@Override
	public Notice selectNoticeOne(int no) {
		Notice notice = noticeDao.selectNoticeOne(no);
		notice.setNotCount(notice.getNotCount()+1);
		
		int countUpdate = noticeDao.updateNoticeCount(notice);
		
		return notice;
	}

	@Override
	public int updateNotice(Notice notice) {
		return noticeDao.updateNotice(notice);
	}

	@Override
	public int deleteNotice(int no) {
		return noticeDao.deleteNotice(no);
	}
	
}
