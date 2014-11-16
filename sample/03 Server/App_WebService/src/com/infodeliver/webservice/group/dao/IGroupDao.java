package com.infodeliver.webservice.group.dao;

import java.util.List;
import java.util.Map;

public interface IGroupDao {

	List<Map<String, Object>> getGroupList(String userId);
	
	List<Map<String, Object>> getGroupMemberList(int groupId, String userId);
}
