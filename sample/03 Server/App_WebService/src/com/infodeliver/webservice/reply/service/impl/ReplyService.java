package com.infodeliver.webservice.reply.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.infodeliver.webservice.common.service.AbstractBaseService;
import com.infodeliver.webservice.reply.dao.IReplyDao;
import com.infodeliver.webservice.reply.service.IReplyService;

public class ReplyService extends AbstractBaseService implements IReplyService {

	@Autowired
	private IReplyDao replyDao;

	@Override
	public List<Map<String, Object>> getReplyList(int noticeId) {
		List<Map<String, Object>> list = replyDao.getReplyList(noticeId);
		return list;
	}

	@Override
	public int saveReply(int noticeId, String userId, String replyText) {
		int saveCode = replyDao.saveReply(noticeId, userId, replyText);
		return saveCode;
	}

}
