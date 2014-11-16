package com.infodeliver.webservice.push.controller;

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
import com.infodeliver.webservice.push.service.IPushService;

@Controller
public class PushController extends AbstractBaseController {

	@Autowired
	private IPushService pushService;
	
	@RequestMapping(value = "/getPushNoticeCount", method = RequestMethod.POST)
	public @ResponseBody
	ResponseResults getPushNoticeCount(@RequestBody RequestParameters params) {

		String userId = "";
		logger.debug("userId:" + params.getParameter("user_id"));
		userId = params.getParameter("user_id");

		Map<String, Object>  pushNoticeMap = pushService.getPushNoticeCount(userId);
		ResponseResults results = new ResponseResults(params);
		results.setResp("00");
		results.putResultAll(pushNoticeMap);
		return results;
	}
	
	@RequestMapping(value = "/getPushNoticeList", method = RequestMethod.POST)
	public @ResponseBody
	ResponseResults getPushNoticeList(@RequestBody RequestParameters params) {

		String userId = "";
		logger.debug("userId:" + params.getParameter("user_id"));
		userId = params.getParameter("user_id");

		List<Map<String, Object>>  pushNoticeList = pushService.getPushNoticeList(userId);
		ResponseResults results = new ResponseResults(params);
		results.setResp("00");
		results.putResult("push_notice_list" ,pushNoticeList);
		return results;
	}
}
