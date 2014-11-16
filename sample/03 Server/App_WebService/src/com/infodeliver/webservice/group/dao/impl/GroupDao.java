package com.infodeliver.webservice.group.dao.impl;

import java.util.List;
import java.util.Map;

import com.infodeliver.webservice.common.dao.AbstractBaseDao;
import com.infodeliver.webservice.group.dao.IGroupDao;

public class GroupDao extends AbstractBaseDao implements IGroupDao {

	@Override
	public List<Map<String, Object>> getGroupList(String userId) {
		String sql = " SELECT " + " gu.GROUP_ID as id,"
				+ " g.GROUP_NAME as groupName" + " FROM " + " t_group_user gu,"
				+ " t_group g" + " WHERE" + " g.ID = gu.GROUP_ID"
				+ " and gu.USER_ID = ?" + " ORDER BY" + " gu.CREATE_DATE";

		return queryForList(sql, new Object[] { userId });
	}

	@Override
	public List<Map<String, Object>> getGroupMemberList(int groupId,
			String userId) {
		String sql = " SELECT "
					+ " u.ID as id,"
					+ " gu.GROUP_ID as groupId,"
					+ " u.USER_NAME as memberName"
					+ " FROM "
					+ " t_group_user gu,"
					+ " t_user u"
					+ " WHERE"
					+ " u.ID = gu.USER_ID"
					+ " and gu.USER_ID <> ?"
					+ " and gu.GROUP_ID = ?"
					+ " ORDER BY"
					+ " gu.CREATE_DATE";

		return queryForList(sql, new Object[] { userId ,groupId});
	}

}
