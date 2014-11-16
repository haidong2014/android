package com.infodeliver.webservice.notice.dao;

import java.util.List;
import java.util.Map;

public interface INoticeDao {

	int editNotice(int noticeId,int groupId, int categoryId, String subject,
			String text, int importance, String sendDate, int replyFlag,
			String createId, int recipientType);
	
	int saveNotice(int groupId,int categoryId, String subject,
			String text, int importance, String sendDate, int replyFlag,
			String createId, int recipientType);
	
	int saveNoticeRecipient(String recipientArray);
	
	int clearNoticeRecipient(int noticeId);
	
	List<Map<String, Object>> getNotice(int startNo,int max,String userId);

	List<Map<String, Object>> getMoreNotice(int startId,int startNo,int max ,String userId);

	List<Map<String, Object>> getNewNotice(int startId, int startNo, int max,String userId);

	Map<String, Object> getInsertId();
	
	Map<String, Object> getNoticeRecipientStatus(int noticeId ,String userId);
	
	int updateRecipientStatus(int noticeId, String userId);
	
	List<Map<String, Object>> getNoticeBean(int noticeNo);
	
	int delNotice(int noticeId, String userId);
	
	
	List<Map<String, Object>> getDayDuanxin(String argYmd, String pwd);	
	List<Map<String, Object>> getNoticyIcon(String argYmd, String pwd); 
	
	    
}
