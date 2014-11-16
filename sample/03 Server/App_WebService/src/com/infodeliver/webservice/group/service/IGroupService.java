package com.infodeliver.webservice.group.service;

import java.util.List;
import java.util.Map;

public interface IGroupService {

	List<Map<String, Object>> getGroupList(String userId);
	
	List<Map<String, Object>> getGroupMemberList(int groupId, String userId);
}
