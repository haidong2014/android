package com.infodeliver.webservice.reply.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.infodeliver.webservice.common.controller.AbstractBaseController;
import com.infodeliver.webservice.common.dto.RequestParameters;
import com.infodeliver.webservice.common.dto.ResponseResults;
import com.infodeliver.webservice.reply.service.IReplyService;

@Controller
public class ReplyController extends AbstractBaseController {

	@Autowired
	private IReplyService replyService;

	@RequestMapping(value = "/getReplyList", method = RequestMethod.POST)
	public @ResponseBody
	ResponseResults getReplyList(@RequestBody RequestParameters params) {

		int  noticeId = -1;
		logger.debug("noticeId:" + params.getParameter("notice_id"));

		noticeId = Integer.parseInt(params.getParameter("notice_id"));

		List<Map<String, Object>> list = replyService.getReplyList(noticeId);

		ResponseResults results = new ResponseResults(params);
		if (!list.isEmpty()) {
			logger.debug("count:" + list.size());
		}
		results.putResult("reply_list", list);
		return results;
	}
	
	@RequestMapping(value = "/saveReply", method = RequestMethod.POST)
	public @ResponseBody
	ResponseResults saveReply(@RequestBody RequestParameters params) {

		int noticeId = -1;
		String userId;
		String replyText;
		logger.debug("noticeId:" + params.getParameter("notice_id"));
		logger.debug("userId:" + params.getParameter("user_id"));
		logger.debug("replyText:" + params.getParameter("reply_text"));

		noticeId = Integer.parseInt(params.getParameter("notice_id"));
		userId = params.getParameter("user_id");
		replyText = params.getParameter("reply_text");

		int saveCode = replyService.saveReply(noticeId, userId, replyText);
		ResponseResults results = new ResponseResults(params);
		return results;
	}
}
