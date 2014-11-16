package com.infodeliver.webservice.notice.dao.impl;

import java.util.List;
import java.util.Map;

import com.infodeliver.webservice.common.dao.AbstractBaseDao;
import com.infodeliver.webservice.notice.dao.INoticeDao;

public class NoticeDao extends AbstractBaseDao implements INoticeDao {
    @Override
    public int saveNotice(int groupId,int categoryId, String subject, String text,
            int importance, String sendDate, int replyFlag, String createId,
            int recipientType) {
        String sql = "INSERT INTO T_NOTICE " + "(" + "CATEGORY_ID" + ",SUBJECT"
                + ",TEXT" + ",IMPORTANCE" + ",SEND_DATE" + ",REPLY_FLAG"
                + ",RECIPIENT_TYPE" + ",CREATE_ID" + ",GROUP_ID" + " ) "
                + "VALUES (?,?,?,?,?,?,?,?,?)";
        Object[] params = new Object[9];
        params[0] = categoryId;
        params[1] = subject;
        params[2] = text;
        params[3] = importance;
        params[4] = sendDate;
        params[5] = replyFlag;
        params[6] = recipientType;
        params[7] = createId;
        params[8] = groupId;
        return update(sql, params);
    }

    @Override
    public int editNotice(int noticeId, int groupId, int categoryId, String subject,
            String text, int importance, String sendDate, int replyFlag,
            String createId, int recipientType) {
        String sql = "UPDATE T_NOTICE " + "SET "
                + "CATEGORY_ID = ? "
                + ",SUBJECT = ? "
                + ",TEXT = ? "
                + ",IMPORTANCE = ? "
                + ",SEND_DATE = ? "
                + ",REPLY_FLAG = ? "
                + ",RECIPIENT_TYPE = ? "
                + ",CREATE_ID = ? "
                + ",GROUP_ID = ? "
                + " WHERE "
                + " ID = ? ";
        Object[] params = new Object[10];
        params[0] = categoryId;
        params[1] = subject;
        params[2] = text;
        params[3] = importance;
        params[4] = sendDate;
        params[5] = replyFlag;
        params[6] = recipientType;
        params[7] = createId;
        params[8] = groupId;
        params[9] = noticeId;
        
        return update(sql, params);
    }

    @Override
    public List<Map<String, Object>> getNotice(int startNo, int max,
            String userId) {

        String sql = " SELECT " + " n.ID AS id, "
                + " n.CATEGORY_ID AS categoryId, "
//                + " g.GROUP_NAME AS groupName, "
                + " n. SUBJECT AS subjectText, " + " n.TEXT AS text, "
                + " n.IMPORTANCE AS importance, "
                + " n.SEND_DATE AS sendDate, "
                + " DATE_FORMAT(n.CREATE_DATE, '%Y-%m-%d') AS sendDay, "
                + " n.REPLY_FLAG AS replyFlag, " + " n.DEL_FLAG AS delFlag, "
                + " n.CREATE_ID AS userId, " + " n.CREATE_DATE AS createDate, "
                + " n.UPDATE_DATE AS updateDate, "
                + " r.PUSH_FLAG AS pushFlag, " + " r.READ_FLAG AS readFlag, "
                + " u.USER_NAME AS userName " + " FROM " + " t_notice n, "
                + " t_notice_recipient r, " + " t_user u " 
//                + ",t_group g"
                + " WHERE " + " n.DEL_FLAG = 0 " + " AND r.DEL_FLAG = 0 "
//                + " AND n.GROUP_ID = g.ID "
                + " AND n.CREATE_ID = u.ID " + " AND n.ID = r.NOTICE_ID "
                + " AND r.RECIPIENT_ID = ? " + " ORDER BY "
                + " n.CREATE_DATE DESC " + " LIMIT ? , ? ";

        return queryForList(sql, new Object[]{userId, startNo, max});
    }

    @Override
    public List<Map<String, Object>> getMoreNotice(int startId, int startNo,
            int max, String userId) {

        String sql = " SELECT " + " n.ID AS id, "
                + " n.CATEGORY_ID AS categoryId, "
//                + " g.GROUP_NAME AS groupName, "
                + " n. SUBJECT AS subjectText, " + " n.TEXT AS text, "
                + " n.IMPORTANCE AS importance, "
                + " n.SEND_DATE AS sendDate, "
                + " DATE_FORMAT(n.CREATE_DATE, '%Y-%m-%d') AS sendDay, "
                + " n.REPLY_FLAG AS replyFlag, " + " n.DEL_FLAG AS delFlag, "
                + " n.CREATE_ID AS userId, " + " n.CREATE_DATE AS createDate, "
                + " n.UPDATE_DATE AS updateDate, "
                + " r.PUSH_FLAG AS pushFlag, " + " r.READ_FLAG AS readFlag, "
                + " u.USER_NAME AS userName " + " FROM " + " t_notice n, "
                + " t_notice_recipient r, " + " t_user u " 
//                + ",t_group g"
                + " WHERE " + " n.DEL_FLAG = 0 " + " AND r.DEL_FLAG = 0 "
//                + " AND n.GROUP_ID = g.ID "
                + " AND n.CREATE_ID = u.ID " + " AND n.ID = r.NOTICE_ID "
                + " AND n.ID < ? AND r.RECIPIENT_ID = ? "
                + " ORDER BY " + " n.CREATE_DATE DESC " + " LIMIT ? , ?";

        return queryForList(sql, new Object[]{startId, userId, startNo, max});
    }

    @Override
    public List<Map<String, Object>> getNewNotice(int startId, int startNo,
            int max, String userId) {
        String sql = " SELECT " + " n.ID AS id, "
                + " n.CATEGORY_ID AS categoryId, "
//                + " g.GROUP_NAME AS groupName, "
                + " n. SUBJECT AS subjectText, " + " n.TEXT AS text, "
                + " n.IMPORTANCE AS importance, "
                + " n.SEND_DATE AS sendDate, "
                + " DATE_FORMAT(n.CREATE_DATE, '%Y-%m-%d') AS sendDay, "
                + " n.REPLY_FLAG AS replyFlag, " + " n.DEL_FLAG AS delFlag, "
                + " n.CREATE_ID AS userId, " + " n.CREATE_DATE AS createDate, "
                + " n.UPDATE_DATE AS updateDate, "
                + " r.PUSH_FLAG AS pushFlag, " + " r.READ_FLAG AS readFlag, "
                + " u.USER_NAME AS userName " + " FROM " + " t_notice n, "
                + " t_notice_recipient r, " + " t_user u " 
//                + ",t_group g"
                + " WHERE " + " n.DEL_FLAG = 0 " + " AND r.DEL_FLAG = 0 "
//                + " AND n.GROUP_ID = g.ID "
                + " AND n.CREATE_ID = u.ID " + " AND n.ID = r.NOTICE_ID "
                + " AND n.ID > ? AND r.RECIPIENT_ID = ? "
                + " ORDER BY " + " n.CREATE_DATE DESC " + " LIMIT ? , ?";

        return queryForList(sql, new Object[]{startId, userId, startNo, max});
    }

    @Override
    public int saveNoticeRecipient(String recipientArrayEditSql) {
        String sql = "INSERT INTO T_NOTICE_RECIPIENT " + "VALUES "
                + recipientArrayEditSql;
        return update(sql);
    }

    @Override
    public Map<String, Object> getInsertId() {
        String sql = "select LAST_INSERT_ID() as insert_id; ";

        return queryForMap(sql);
    }

    @Override
    public Map<String, Object> getNoticeRecipientStatus(int noticeId,
            String userId) {
        String sql = "SELECT " + " sum(r.READ_FLAG) AS get_all, "
                + " count(r.READ_FLAG) AS recipient_all " + " FROM "
                + " t_notice_recipient r " + " WHERE " + " r.NOTICE_ID = ? "
                + " AND r.RECIPIENT_ID <> ? "
                + " GROUP BY " + " r.NOTICE_ID ";

        return queryForMap(sql, new Object[]{noticeId, userId});
    }

    @Override
    public int updateRecipientStatus(int noticeId, String userId) {
        String sql = "UPDATE " + " t_notice_recipient r "
                + " SET READ_FLAG = '1' " + " WHERE " + " r.RECIPIENT_ID = ? "
                + " AND r.NOTICE_ID = ? ";

        return update(sql, new Object[]{userId, noticeId});
    }

    @Override
    public List<Map<String, Object>> getNoticeBean(int noticeNo) {
        String sql = " SELECT " + " n.ID AS id, "
                + " n.CATEGORY_ID AS categoryId, "
                + " g.GROUP_NAME AS groupName, "
                + " n. SUBJECT AS subjectText, " + " n.TEXT AS text, "
                + " n.IMPORTANCE AS importance, "
                + " n.SEND_DATE AS sendDate, "
                + " DATE_FORMAT(n.CREATE_DATE, '%Y-%m-%d') AS sendDay, "
                + " n.REPLY_FLAG AS replyFlag, " + " n.DEL_FLAG AS delFlag, "
                + " n.CREATE_ID AS userId, " + " n.CREATE_DATE AS createDate, "
                + " n.UPDATE_DATE AS updateDate, "
                + " u.USER_NAME AS userName " + " FROM " + " t_notice n, "
                + " t_user u " 
                + ",t_group g"
                + " WHERE " + " n.DEL_FLAG = 0 "
                + " AND n.GROUP_ID = g.ID "
                + " AND n.CREATE_ID = u.ID " + " AND n.ID = ? "
                + " ORDER BY " + " n.CREATE_DATE DESC ";

        return queryForList(sql, new Object[]{noticeNo});
    }

    @Override
    public int delNotice(int noticeId, String userId) {
        String sql = "UPDATE " + " t_notice_recipient r "
                + " SET DEL_FLAG = '1' " + " WHERE " + " r.RECIPIENT_ID = ? "
                + " AND r.NOTICE_ID = ? ";

        return update(sql, new Object[]{userId, noticeId});
    }

    @Override
    public List<Map<String, Object>> getDayDuanxin(String argYmd, String pwd) {
        String sql = "SELECT * FROM t_notice "
                + " WHERE  DATE_FORMAT(SEND_DATE,'%Y/%m/%d')  = ?";

        return queryForList(sql, new Object[]{argYmd});
    }

    @Override
    public List<Map<String, Object>> getNoticyIcon(String argYmd, String pwd) {
        String sql = "SELECT ID,date_format(SEND_DATE,'%Y/%m/%d') as DATE1,CATEGORY_ID FROM t_notice "
                + " WHERE  DATE_FORMAT(SEND_DATE,'%Y/%m/%d')  >= ?"
                + " ORDER BY SEND_DATE DESC";

        return queryForList(sql, new Object[]{argYmd});
    }

    @Override
    public int clearNoticeRecipient(int noticeId) {
        String sql = "DELETE FROM " + " t_notice_recipient " + " WHERE "
                + " NOTICE_ID = ? ";
        return update(sql, new Object[]{noticeId});
    }

}
