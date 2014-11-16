package com.infodeliver.webservice.reply.dao.impl;

import java.util.List;
import java.util.Map;

import com.infodeliver.webservice.common.dao.AbstractBaseDao;
import com.infodeliver.webservice.reply.dao.IReplyDao;

public class ReplyDao extends AbstractBaseDao implements IReplyDao {

	@Override
	public List<Map<String, Object>> getReplyList(int groupId) {
		String sql = " SELECT"
					+ " r.ID AS id,"
					+ " r.NOTICE_ID AS noticeId,"
					+ " r.REPLY_TEXT AS replyText,"
					+ " u.ID AS replyUserId,"
					+ " u.USER_NAME AS replyUserName,"
					+ " r.CREATE_DATE AS createTime"
					+ " FROM"
					+ " t_notice_reply r,"
					+ " t_user u"
					+ " WHERE"
					+ " r.REPLY_USER_ID = u.ID"
					+ " AND r.DEL_FLAG = 0"
					+ " AND r.NOTICE_ID = ?"
					+ " ORDER BY "
					+ " r.CREATE_DATE DESC";

		return queryForList(sql, new Object[] { groupId});
	}

	@Override
	public int saveReply(int noticeId, String userId, String replyText) {
		String sql = "INSERT INTO t_notice_reply " + "(" 
				+ "NOTICE_ID" 
				+ ",REPLY_USER_ID"
                + ",REPLY_TEXT" 
                + ",CREATE_ID" + " ) "
                + "VALUES (?,?,?,?)";
        Object[] params = new Object[4];
        params[0] = noticeId;
        params[1] = userId;
        params[2] = replyText;
        params[3] = userId;
        return update(sql, params);
	}

}
