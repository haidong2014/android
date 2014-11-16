package com.infodeliver.webservice.group.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.infodeliver.webservice.common.service.AbstractBaseService;
import com.infodeliver.webservice.group.dao.IGroupDao;
import com.infodeliver.webservice.group.service.IGroupService;

public class GroupService extends AbstractBaseService implements IGroupService {

	@Autowired
	private IGroupDao groupDao;

	@Override
	public List<Map<String, Object>> getGroupList(String userId) {
		List<Map<String, Object>> list = groupDao.getGroupList(userId);
		return list;
	}

	@Override
	public List<Map<String, Object>> getGroupMemberList(int groupId,
			String userId) {
		List<Map<String, Object>> list = groupDao.getGroupMemberList(groupId, userId);
		return list;
	}

}
