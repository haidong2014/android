package com.infodeliver.webservice.reply.dao;

import java.util.List;
import java.util.Map;

public interface IReplyDao {

	List<Map<String, Object>> getReplyList(int noticeId);

	int saveReply(int noticeId, String userId, String replyText);
}
