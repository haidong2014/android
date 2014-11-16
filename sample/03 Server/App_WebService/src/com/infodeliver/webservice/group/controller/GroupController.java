package com.infodeliver.webservice.group.controller;

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
import com.infodeliver.webservice.group.service.IGroupService;

@Controller
public class GroupController extends AbstractBaseController {

	@Autowired
	private IGroupService groupService;

	@RequestMapping(value = "/getGroupList", method = RequestMethod.POST)
	public @ResponseBody
	ResponseResults getGroupList(@RequestBody RequestParameters params) {

		String userId = "";
		logger.debug("userId:" + params.getParameter("user_id"));

		userId = params.getParameter("user_id");

		List<Map<String, Object>> list = groupService.getGroupList(userId);

		ResponseResults results = new ResponseResults(params);
		if (!list.isEmpty()) {
			logger.debug("count:" + list.size());
		}
		results.putResult("group_list", list);
		return results;
	}
	
	@RequestMapping(value = "/getGroupMemberList", method = RequestMethod.POST)
	public @ResponseBody
	ResponseResults getGroupMemberList(@RequestBody RequestParameters params) {

		int groupId = -1;
		String userId = "";
		logger.debug("userId:" + params.getParameter("user_id"));
		logger.debug("groupId:" + params.getParameter("group_id"));

		groupId = Integer.parseInt(params.getParameter("group_id"));
		userId = params.getParameter("user_id");

		List<Map<String, Object>> list = groupService.getGroupMemberList(groupId, userId);

		ResponseResults results = new ResponseResults(params);
		if (!list.isEmpty()) {
			logger.debug("count:" + list.size());
		}
		results.putResult("group_member_list", list);
		return results;
	}
}
