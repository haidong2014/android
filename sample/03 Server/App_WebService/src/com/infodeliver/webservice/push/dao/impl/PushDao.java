package com.infodeliver.webservice.push.dao.impl;

import java.util.List;
import java.util.Map;

import com.infodeliver.webservice.common.dao.AbstractBaseDao;
import com.infodeliver.webservice.push.dao.IPushDao;

public class PushDao extends AbstractBaseDao implements IPushDao {

    @Override
    public Map<String, Object> getPushNoticeCount(String userId) {
        String sql = "SELECT "
                + " count(*) AS new_notice_size "
                + " FROM "
                    + " t_notice n, "
                    + " t_notice_recipient r "
                + " WHERE "
                    + " n.ID = r.NOTICE_ID "
                + " AND r.PUSH_FLAG = 0 "
                + " AND DATE_FORMAT(n.CREATE_DATE, '%Y-%m-%d') = CURDATE() "
                + " AND r.RECIPIENT_ID = ? ";
        return queryForMap(sql, new Object[]{userId});
    }

    @Override
    public List<Map<String, Object>> getPushNoticeList(String userId) {
        String sql = "SELECT "
                + " n.ID AS id, "
                + " n.CATEGORY_ID AS categoryId, "
                + " n. SUBJECT AS subjectText, "
                + " n.TEXT AS text, "
                + " n.IMPORTANCE AS importance, "
                + " n.SEND_DATE AS sendDate, "
                + " DATE_FORMAT(n.CREATE_DATE, '%Y-%m-%d') AS sendDay, "
                + " n.REPLY_FLAG AS replyFlag, "
                + " n.DEL_FLAG AS delFlag, "
                + " n.CREATE_ID AS userId, "
                + " n.CREATE_DATE AS createDate, "
                + " n.UPDATE_DATE AS updateDate, "
                + " r.PUSH_FLAG AS pushFlag, "
                + " r.READ_FLAG AS readFlag, "
                + " u.USER_NAME AS userName "
            + " FROM "
                + " t_notice n, "
                + " t_notice_recipient r, "
                + " t_user u "
            + " WHERE "
                + " n.DEL_FLAG = 0 "
            + " AND r.DEL_FLAG = 0 "
            + " AND n.CREATE_ID = u.ID "
            + " AND r.PUSH_FLAG = 0 "
            + " AND DATE_FORMAT(n.CREATE_DATE, '%Y-%m-%d') = CURDATE() "
            + " AND n.ID = r.NOTICE_ID "
            + " AND r.RECIPIENT_ID <> n.CREATE_ID "
            + " AND r.RECIPIENT_ID = ? "
            + " ORDER BY "
                + " n.CREATE_DATE ASC ";
        return queryForList(sql, new Object[]{userId});
    }

}
