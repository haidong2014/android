package com.infodeliver.webservice.notice.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface INoticeService {

	long editNotice(int noticeId,int groupId, int categoryId, String subject, String text, int importance,
			String sendDate, int replyFlag, String createId,
			String recipientArray, int recipientType);
	
	long saveNotice(int categoryId,int groupId, String subject, String text, int importance,
			String sendDate, int replyFlag, String createId,
			String recipientArray, int recipientType);

	List<Map<String, Object>> getNotice(int startNo, int max,String userId);

	List<Map<String, Object>> getMoreNotice(int startId, int startNo, int max,String userId);

	List<Map<String, Object>> getNewNotice(int startId, int startNo, int max,String userId);
	
	Map<String, Object> getNoticeRecipientStatus(int noticeId ,String userId);
	
	int updateRecipientStatus(int noticeId, String userId);
	
	List<Map<String, Object>> getNoticeBean(int noticeNo);
	
	List<Map<String, Object>> getNoticeWithRecipientStatus(int noticeNo, String userId);
	
//	List<Map<String, Object>> getNoticeWithRecipientStatusUpdate(int noticeNo, String userId);
	List<Map<String, Object>> getNoticeWithRecipientStatusUpdate(int noticeId,
			String userId, boolean needChangeRecipientStatus);	
	
	int delNotice(int noticeId, String userId);
	
	
	List<Map<String, Object>> getDayDuanxin(String phone, String pwd);	
    List<Map<String, Object>> getIcons(String phone, String pwd);


	    
	
}
