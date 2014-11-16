package com.infodeliver.webservice.notice.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.infodeliver.webservice.common.service.AbstractBaseService;
import com.infodeliver.webservice.notice.dao.INoticeDao;
import com.infodeliver.webservice.notice.service.INoticeService;

public class NoticeService extends AbstractBaseService implements
        INoticeService {

    @Autowired
    private INoticeDao noticeDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public long editNotice(int noticeId,int groupId, int categoryId, String subject, String text,
            int importance, String sendDate, int replyFlag, String createId,
            String recipientArray, int recipientType) {

        // edit notice
        noticeDao.editNotice(noticeId, groupId, categoryId, subject, text,
                importance, sendDate, replyFlag, createId, recipientType);

        //delete recipient
        noticeDao.clearNoticeRecipient(noticeId);

        StringBuffer sb = new StringBuffer();
        sb.append(recipientArray);
        // save recipient
        StringBuffer saveRecipientArraySql = new StringBuffer();
        if (recipientType == 0) {
            String[] al = sb.toString().split(",");
            for (int i = 0; i < al.length; i++) {
                saveRecipientArraySql.append("(");
                saveRecipientArraySql.append(noticeId);
                saveRecipientArraySql.append(",");
                saveRecipientArraySql.append(al[i]);
                saveRecipientArraySql.append(",");
                saveRecipientArraySql.append("0");
                saveRecipientArraySql.append(",");
                saveRecipientArraySql.append("0");
                saveRecipientArraySql.append(",");
                saveRecipientArraySql.append("0");
                saveRecipientArraySql.append(")");
                saveRecipientArraySql.append(",");
            }
            //creator
            saveRecipientArraySql.append("(");
            saveRecipientArraySql.append(noticeId);
            saveRecipientArraySql.append(",");
            saveRecipientArraySql.append(createId);
            saveRecipientArraySql.append(",");
            saveRecipientArraySql.append("1");
            saveRecipientArraySql.append(",");
            saveRecipientArraySql.append("0");
            saveRecipientArraySql.append(",");
            saveRecipientArraySql.append("0");
            saveRecipientArraySql.append(")");
        } else {
            //creator
            saveRecipientArraySql.append("(");
            saveRecipientArraySql.append(noticeId);
            saveRecipientArraySql.append(",");
            saveRecipientArraySql.append(createId);
            saveRecipientArraySql.append(",");
            saveRecipientArraySql.append("1");
            saveRecipientArraySql.append(",");
            saveRecipientArraySql.append("0");
            saveRecipientArraySql.append(",");
            saveRecipientArraySql.append("0");
            saveRecipientArraySql.append(")");
        }

        logger.debug("saveRecipientArraySql:"
                + saveRecipientArraySql.toString());
        int saveRecipientCode = noticeDao
                .saveNoticeRecipient(saveRecipientArraySql.toString());
        logger.debug("saveNoticeCode:" + saveRecipientCode);

        return noticeId;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public long saveNotice(int groupId, int categoryId, String subject, String text,
            int importance, String sendDate, int replyFlag, String createId,
            String recipientArray, int recipientType) {

        // save notice
        int saveNoticeCode = noticeDao.saveNotice(groupId,categoryId, subject, text,
                importance, sendDate, replyFlag, createId, recipientType);
        logger.debug("saveNoticeCode:" + saveNoticeCode);
        // get insert id
        Map selectResMap = noticeDao.getInsertId();
        logger.debug("insertId:" + selectResMap.get("insert_id"));
        long insertId = (long) selectResMap.get("insert_id");

        StringBuffer sb = new StringBuffer();
        sb.append(recipientArray);
//		sb.append("," + createId);

        // save recipient
        StringBuffer saveRecipientArraySql = new StringBuffer();
        if (recipientType == 0) {
            String[] al = sb.toString().split(",");
            for (int i = 0; i < al.length; i++) {
                saveRecipientArraySql.append("(");
                saveRecipientArraySql.append(insertId);
                saveRecipientArraySql.append(",");
                saveRecipientArraySql.append(al[i]);
                saveRecipientArraySql.append(",");
                saveRecipientArraySql.append("0");
                saveRecipientArraySql.append(",");
                saveRecipientArraySql.append("0");
                saveRecipientArraySql.append(",");
                saveRecipientArraySql.append("0");
                saveRecipientArraySql.append(")");
                saveRecipientArraySql.append(",");
            }
            //creator
            saveRecipientArraySql.append("(");
            saveRecipientArraySql.append(insertId);
            saveRecipientArraySql.append(",");
            saveRecipientArraySql.append(createId);
            saveRecipientArraySql.append(",");
            saveRecipientArraySql.append("1");
            saveRecipientArraySql.append(",");
            saveRecipientArraySql.append("0");
            saveRecipientArraySql.append(",");
            saveRecipientArraySql.append("0");
            saveRecipientArraySql.append(")");
        } else {
            //creator
            saveRecipientArraySql.append("(");
            saveRecipientArraySql.append(insertId);
            saveRecipientArraySql.append(",");
            saveRecipientArraySql.append(createId);
            saveRecipientArraySql.append(",");
            saveRecipientArraySql.append("1");
            saveRecipientArraySql.append(",");
            saveRecipientArraySql.append("0");
            saveRecipientArraySql.append(",");
            saveRecipientArraySql.append("0");
            saveRecipientArraySql.append(")");
        }

        logger.debug("saveRecipientArraySql:"
                + saveRecipientArraySql.toString());
        int saveRecipientCode = noticeDao
                .saveNoticeRecipient(saveRecipientArraySql.toString());
        logger.debug("saveNoticeCode:" + saveRecipientCode);

        return insertId;
    }

    @Override
    @Transactional(propagation = Propagation.NEVER, readOnly=true)
    public List<Map<String, Object>> getNotice(int startNo, int max,
            String userId) {
        List<Map<String, Object>> list = noticeDao.getNotice(startNo, max,
                userId);
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.NEVER, readOnly=true)
    public List<Map<String, Object>> getMoreNotice(int startId, int startNo,
            int max, String userId) {
        List<Map<String, Object>> list = noticeDao.getMoreNotice(startId,
                startNo, max, userId);
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.NEVER, readOnly=true)
    public List<Map<String, Object>> getNewNotice(int startId, int startNo,
            int max, String userId) {
        List<Map<String, Object>> list = noticeDao.getNewNotice(startId,
                startNo, max, userId);
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.NEVER, readOnly=true)
    public Map<String, Object> getNoticeRecipientStatus(int noticeId,
            String userId) {
        Map<String, Object> noticeMap = noticeDao.getNoticeRecipientStatus(
                noticeId, userId);
        return noticeMap;
    }

    @Override
    public int updateRecipientStatus(int noticeId, String userId) {
        int updateCode = noticeDao.updateRecipientStatus(noticeId, userId);
        return updateCode;
    }

    @Override
    @Transactional(propagation = Propagation.NEVER, readOnly=true)
    public List<Map<String, Object>> getNoticeBean(int noticeNo) {
        List<Map<String, Object>> list = noticeDao.getNoticeBean(noticeNo);
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Map<String, Object>> getNoticeWithRecipientStatus(int noticeId,
            String userId) {

        // get notice bean
        List<Map<String, Object>> list = noticeDao.getNoticeBean(noticeId);
        // get recipient status
        Map<String, Object> recipientMap = noticeDao.getNoticeRecipientStatus(
                noticeId, userId);

        if (list.size() > 0) {
            list.get(0).put("getAll", recipientMap.get("get_all"));
            list.get(0).put("recipientAll", recipientMap.get("recipient_all"));
        }

        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Map<String, Object>> getNoticeWithRecipientStatusUpdate(
            int noticeId, String userId, boolean needChangeRecipientStatus) {

        // get notice bean
        List<Map<String, Object>> list = noticeDao.getNoticeBean(noticeId);
        // update recipient status
        if (needChangeRecipientStatus) {
             int updateCode = noticeDao.updateRecipientStatus(noticeId, userId);
        }

        return list;
    }

    @Override
    public int delNotice(int noticeId, String userId) {
        int updateCode = noticeDao.delNotice(noticeId, userId);
        return updateCode;
    }


    @Override
    @Transactional(propagation = Propagation.NEVER, readOnly=true)
    public List<Map<String, Object>> getDayDuanxin(String phone, String pwd){
        List<Map<String, Object>> list = noticeDao.getDayDuanxin(phone,pwd);

        return list;
    }

    @Override
    @Transactional(propagation = Propagation.NEVER, readOnly=true)
    public List<Map<String, Object>> getIcons(String phone, String pwd)
    {
        List<Map<String, Object>> list = noticeDao.getNoticyIcon(phone,pwd);

        return list;
    }

}
