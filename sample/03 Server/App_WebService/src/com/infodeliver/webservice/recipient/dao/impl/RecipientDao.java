package com.infodeliver.webservice.recipient.dao.impl;

import java.util.List;
import java.util.Map;

import com.infodeliver.webservice.common.dao.AbstractBaseDao;
import com.infodeliver.webservice.recipient.dao.IRecipientDao;

public class RecipientDao extends AbstractBaseDao implements IRecipientDao {

    @Override
    public List<Map<String, Object>> getRecipientStatusList(int noticeId, String userId) {
        String sql = " SELECT "
                        + " r.NOTICE_ID AS noticeId, "
                        + " r.RECIPIENT_ID AS recipientId, "
                        + " r.READ_FLAG AS readFlag, "
                        + " r.PUSH_FLAG AS pushFlag, "
                        + " u.USER_NAME AS recipientName "
                    + " FROM "
                        + " t_notice_recipient r, "
                        + " t_user u "
                    + " WHERE "
                        + " r.RECIPIENT_ID = u.ID "
                    + " AND r.RECIPIENT_ID <> ? "
                    + " AND r.NOTICE_ID = ? ";

        return queryForList(sql, new Object[]{userId, noticeId});
    }

}
