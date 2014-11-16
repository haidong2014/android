package com.infodeliver.webservice.recipient.controller;

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
import com.infodeliver.webservice.recipient.service.IRecipientService;

@Controller
public class RecipientController extends AbstractBaseController {

	@Autowired
	private IRecipientService recipientService;

	@RequestMapping(value = "/getRecipientStatusList", method = RequestMethod.POST)
	public @ResponseBody
	ResponseResults getRecipientStatusList(@RequestBody RequestParameters params) {

		int noticeId = 0;
		String userId = "";
		logger.debug("noticeId:" + params.getParameter("notice_id"));
		noticeId = Integer.parseInt(params.getParameter("notice_id"));
		logger.debug("userId:" + params.getParameter("user_id"));
		userId = params.getParameter("user_id");

		List<Map<String, Object>> list = recipientService.getRecipientStatusList(noticeId, userId);
		
		ResponseResults results = new ResponseResults(params);
		results.putResult("recipient_list", list);
		return results;
	}

}
