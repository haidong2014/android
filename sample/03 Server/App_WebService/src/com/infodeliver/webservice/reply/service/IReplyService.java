package com.infodeliver.webservice.reply.service;

import java.util.List;
import java.util.Map;

public interface IReplyService {

	List<Map<String, Object>> getReplyList(int noticeId);
	
	int saveReply(int noticeId, String userId, String replyText);
}
