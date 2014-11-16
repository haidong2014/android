package com.infodeliver.client.utils;

import org.json.JSONException;
import org.json.JSONObject;

import com.infodeliver.db.domain.NoticeBizBean;
import com.infodeliver.db.domain.ReplyBizBean;

/**
 * @author yujian
 * @version 1.0
 * @createDate 2014-06-23
 * @lastUpdate 2014-06-23
 */
public class NSMsgFactory {

    private static final String MESSAGE_VERSION = "1.0";
    private static final String CMD_VINE_SHOW = "vine.json";
    public static final String CMD_NOTICE_SAVE = "saveNotice";
    public static final String CMD_NOTICE_EDIT = "editNotice";
    public static final String CMD_NOTICE_GET = "getNotice";
    public static final String CMD_GROUP_LIST = "getGroupList";
    public static final String CMD_REPLY_LIST = "getReplyList";
    public static final String CMD_REPLY_SAVE = "saveReply";
    public static final String CMD_GROUP_MEMBER_LIST = "getGroupMemberList";
    public static final String CMD_NOTICE_GET_MORE = "getMoreNotice";
    public static final String CMD_NOTICE_GET_NEW = "getNewNotice";
    public static final String CMD_NOTICE_GET_RECIPIENT_STATUS = "getRecipientStatus";
    public static final String CMD_NOTICE_GET_RECIPIENT_STATUS_LIST = "getRecipientStatusList";
    public static final String CMD_NOTICE_GET_NOTICE_WITH_RECIPIENT_STATUS = "getNoticeWithRecipientStatus";
    public static final String CMD_NOTICE_GET_NOTICE_WITH_RECIPIENT_STATUS_UPDATE = "getNoticeWithRecipientStatusUpdate";
    public static final String CMD_NOTICE_UPDATE_RECIPIENT_STATUS = "updateRecipientStatus";
    public static final String CMD_PUSH_GET_NOTICE_SIZE = "getPushNoticeCount";
    public static final String CMD_PUSH_GET_NOTICE_LIST = "getPushNoticeList";
    public static final String CMD_DELETE_NOTICE = "delNotice";
    public static final String CMD_LOGIN = "login";
    public static final String CMD_PASSWORD_RESET = "password_reset";
    public static final String CMD_REGISTER = "register";
    public static final String CMD_CHECK_SMS_CODE = "check_sms_code";
    public static final String CMD_SEND_SMS_CODE = "send_sms_code";
    public static final String CMD_SET_SEX = "set_sex";
    public static final String CMD_GET_USER = "get_user";
    public static final String CMD_MODIFY_NICKNAME = "modify_nickname";
    public static final String CMD_MODIFY_SIGN = "modify_sign";
    public static final String CMD_MODIFY_PWD = "modify_pwd";
    public static final String CMD_BIND_NEW_PHONE = "bind_new_phone";
    public static final String CMD_CHECK_BIND_PHONE = "check_bind_phone";
    public static final String CMD_BIND_SINA = "bind_sina";

    public static final String requestVineListMsg(String userName,
            String password) throws JSONException {
        JSONObject result = new JSONObject();
        result.put(NSBizConstant.KEY_VERSION, MESSAGE_VERSION);
        result.put(NSBizConstant.KEY_CMD, CMD_VINE_SHOW);

        // params
        JSONObject params = new JSONObject();
        params.put(NSBizConstant.KEY_USER_NAME, userName);
        params.put(NSBizConstant.KEY_PASSWORD, password);
        result.put(NSBizConstant.KEY_PARAMS, params);

        return result.toString();
    }

    // notice save
    public static final String requestSaveNoticeMsg(NoticeBizBean notice)
            throws JSONException {
        JSONObject result = new JSONObject();
        result.put(NSBizConstant.KEY_VERSION, MESSAGE_VERSION);
        result.put(NSBizConstant.KEY_CMD, CMD_NOTICE_SAVE);

        // params
        JSONObject params = new JSONObject();
        params.put(NSBizConstant.KEY_NOTICE_CATEGORY_ID, notice.getCategoryId());
        params.put(NSBizConstant.KEY_NOTICE_SUBJECT, notice.getSubjectText());
        params.put(NSBizConstant.KEY_NOTICE_TEXT, notice.getText());
        params.put(NSBizConstant.KEY_NOTICE_IMPORTANCE, notice.getImportance());
        params.put(NSBizConstant.KEY_NOTICE_RECIPIENT_ARRAY,
                notice.getRecipientArray());
        params.put(NSBizConstant.KEY_NOTICE_REPLY_FLAG, notice.getReplyFlag());
        params.put(NSBizConstant.KEY_NOTICE_SEND_DATE, notice.getSendDate());
        params.put(NSBizConstant.KEY_NOTICE_RECIPIENT_TYPE, notice.getRecipinetType());
        params.put(NSBizConstant.KEY_GROUP_ID, notice.getGroupId());

        params.put(NSBizConstant.KEY_COMM_CREATE_ID, notice.getUserId());
        result.put(NSBizConstant.KEY_PARAMS, params);
        return result.toString();
    }
    
    // notice edit
    public static final String requestEditNoticeMsg(NoticeBizBean notice)
            throws JSONException {
        JSONObject result = new JSONObject();
        result.put(NSBizConstant.KEY_VERSION, MESSAGE_VERSION);
        result.put(NSBizConstant.KEY_CMD, CMD_NOTICE_EDIT);

        // params
        JSONObject params = new JSONObject();
        params.put(NSBizConstant.KEY_NOTICES_ID, notice.getId());
        params.put(NSBizConstant.KEY_NOTICE_CATEGORY_ID, notice.getCategoryId());
        params.put(NSBizConstant.KEY_NOTICE_SUBJECT, notice.getSubjectText());
        params.put(NSBizConstant.KEY_NOTICE_TEXT, notice.getText());
        params.put(NSBizConstant.KEY_NOTICE_IMPORTANCE, notice.getImportance());
        params.put(NSBizConstant.KEY_NOTICE_RECIPIENT_ARRAY,
                notice.getRecipientArray());
        params.put(NSBizConstant.KEY_NOTICE_REPLY_FLAG, notice.getReplyFlag());
        params.put(NSBizConstant.KEY_NOTICE_SEND_DATE, notice.getSendDate());
        params.put(NSBizConstant.KEY_NOTICE_RECIPIENT_TYPE, notice.getRecipinetType());
        params.put(NSBizConstant.KEY_GROUP_ID, notice.getGroupId());

        params.put(NSBizConstant.KEY_COMM_CREATE_ID, notice.getUserId());
        result.put(NSBizConstant.KEY_PARAMS, params);
        return result.toString();
    }

    // notice get
    public static final String requestNoticeMsg(int startNo,
            int max ,String userId) throws JSONException {
        JSONObject result = new JSONObject();
        result.put(NSBizConstant.KEY_VERSION, MESSAGE_VERSION);
        result.put(NSBizConstant.KEY_CMD, CMD_NOTICE_GET);

        // params
        JSONObject params = new JSONObject();
        params.put(NSBizConstant.KEY_COMM_START_NO, startNo);
        params.put(NSBizConstant.KEY_COMM_MAX, max);
        params.put(NSBizConstant.KEY_USER_ID, userId);
        result.put(NSBizConstant.KEY_PARAMS, params);
        return result.toString();
    }
    
    // reply
    public static final String requestReplyListMsg(int noticeId) throws JSONException {
        JSONObject result = new JSONObject();
        result.put(NSBizConstant.KEY_VERSION, MESSAGE_VERSION);
        result.put(NSBizConstant.KEY_CMD, CMD_REPLY_LIST);

        // params
        JSONObject params = new JSONObject();
        params.put(NSBizConstant.KEY_NOTICES_ID, noticeId);
        result.put(NSBizConstant.KEY_PARAMS, params);
        return result.toString();
    }
    
    // reply save
    public static final String requestSaveReplyMsg(ReplyBizBean reply)
            throws JSONException {
        JSONObject result = new JSONObject();
        result.put(NSBizConstant.KEY_VERSION, MESSAGE_VERSION);
        result.put(NSBizConstant.KEY_CMD, CMD_REPLY_SAVE);

        // params
        JSONObject params = new JSONObject();
        params.put(NSBizConstant.KEY_NOTICES_ID, reply.getNoticeId());
        params.put(NSBizConstant.KEY_REPLY_TEXT, reply.getReplyText());
        params.put(NSBizConstant.KEY_USER_ID, reply.getReplyUserId());
        params.put(NSBizConstant.KEY_COMM_CREATE_ID, reply.getReplyUserId());
        result.put(NSBizConstant.KEY_PARAMS, params);
        return result.toString();
    }
    
    // group get
    public static final String requestGroupListMsg(String userId) throws JSONException {
        JSONObject result = new JSONObject();
        result.put(NSBizConstant.KEY_VERSION, MESSAGE_VERSION);
        result.put(NSBizConstant.KEY_CMD, CMD_GROUP_LIST);

        // params
        JSONObject params = new JSONObject();
        params.put(NSBizConstant.KEY_USER_ID, userId);
        result.put(NSBizConstant.KEY_PARAMS, params);
        return result.toString();
    }

    // group menber get
    public static final String requestGroupMemberListMsg(int groupId, String userId) throws JSONException {
        JSONObject result = new JSONObject();
        result.put(NSBizConstant.KEY_VERSION, MESSAGE_VERSION);
        result.put(NSBizConstant.KEY_CMD, CMD_GROUP_MEMBER_LIST);

        // params
        JSONObject params = new JSONObject();
        params.put(NSBizConstant.KEY_GROUP_ID, groupId);
        params.put(NSBizConstant.KEY_USER_ID, userId);
        result.put(NSBizConstant.KEY_PARAMS, params);
        return result.toString();
    }
    
    public static final String requestDeleteNoticeMsg(int noticeId,String userId) throws JSONException {
        JSONObject result = new JSONObject();
        result.put(NSBizConstant.KEY_VERSION, MESSAGE_VERSION);
        result.put(NSBizConstant.KEY_CMD, CMD_DELETE_NOTICE);

        // params
        JSONObject params = new JSONObject();
        params.put(NSBizConstant.KEY_NOTICES_ID, noticeId);
        params.put(NSBizConstant.KEY_USER_ID, userId);
        result.put(NSBizConstant.KEY_PARAMS, params);
        return result.toString();
    }
    
    public static final String requestUpdateRecipientStatusMsg(int noticeId,String userId) throws JSONException {
        JSONObject result = new JSONObject();
        result.put(NSBizConstant.KEY_VERSION, MESSAGE_VERSION);
        result.put(NSBizConstant.KEY_CMD, CMD_NOTICE_UPDATE_RECIPIENT_STATUS);

        // params
        JSONObject params = new JSONObject();
        params.put(NSBizConstant.KEY_NOTICES_ID, noticeId);
        params.put(NSBizConstant.KEY_USER_ID, userId);
        result.put(NSBizConstant.KEY_PARAMS, params);
        return result.toString();
    }

    public static final String requestNoticeWithRecipientStatusMsg(int noticeId, String noticeCreatorID) throws JSONException {
        JSONObject result = new JSONObject();
        result.put(NSBizConstant.KEY_VERSION, MESSAGE_VERSION);
        result.put(NSBizConstant.KEY_CMD, CMD_NOTICE_GET_NOTICE_WITH_RECIPIENT_STATUS);

        // params
        JSONObject params = new JSONObject();
        params.put(NSBizConstant.KEY_NOTICES_ID, noticeId);
        params.put(NSBizConstant.KEY_USER_ID, noticeCreatorID);

        result.put(NSBizConstant.KEY_PARAMS, params);
        return result.toString();
    }

    public static final String requestNoticeWithRecipientStatusUpdateMsg(int noticeId, String noticeCreatorID,boolean needChangeFlag) throws JSONException {
        JSONObject result = new JSONObject();
        result.put(NSBizConstant.KEY_VERSION, MESSAGE_VERSION);
        result.put(NSBizConstant.KEY_CMD, CMD_NOTICE_GET_NOTICE_WITH_RECIPIENT_STATUS_UPDATE);

        // params
        JSONObject params = new JSONObject();
        params.put(NSBizConstant.KEY_NOTICES_ID, noticeId);
        params.put(NSBizConstant.KEY_USER_ID, noticeCreatorID);
        params.put(NSBizConstant.KEY_NEED_CHANGE_RECIPIENT_READ_STATUS_FLAG, needChangeFlag);
        result.put(NSBizConstant.KEY_PARAMS, params);
        return result.toString();
    }

    // recipient get
    public static final String requestRecipientStatusListMsg(int noticeId,String noticeCreatorId) throws JSONException {
        JSONObject result = new JSONObject();
        result.put(NSBizConstant.KEY_VERSION, MESSAGE_VERSION);
        result.put(NSBizConstant.KEY_CMD, CMD_NOTICE_GET_RECIPIENT_STATUS_LIST);

        // params
        JSONObject params = new JSONObject();
        params.put(NSBizConstant.KEY_NOTICES_ID, noticeId);
        params.put(NSBizConstant.KEY_USER_ID, noticeCreatorId);
        result.put(NSBizConstant.KEY_PARAMS, params);
        return result.toString();
    }

    public static final String requestRecipientStatusMsg(int noticeId) throws JSONException {
        JSONObject result = new JSONObject();
        result.put(NSBizConstant.KEY_VERSION, MESSAGE_VERSION);
        result.put(NSBizConstant.KEY_CMD, CMD_NOTICE_GET_RECIPIENT_STATUS);

        // params
        JSONObject params = new JSONObject();
        params.put(NSBizConstant.KEY_NOTICES_ID, noticeId);
        result.put(NSBizConstant.KEY_PARAMS, params);
        return result.toString();
    }

    public static final String requestMoreNoticeMsg(int startId, int startNo,
            int max,String userId) throws JSONException {
        JSONObject result = new JSONObject();
        result.put(NSBizConstant.KEY_VERSION, MESSAGE_VERSION);
        result.put(NSBizConstant.KEY_CMD, CMD_NOTICE_GET_MORE);

        // params
        JSONObject params = new JSONObject();
        params.put(NSBizConstant.KEY_COMM_START_ID, startId);
        params.put(NSBizConstant.KEY_COMM_START_NO, startNo);
        params.put(NSBizConstant.KEY_COMM_MAX, max);
        params.put(NSBizConstant.KEY_USER_ID, userId);
        result.put(NSBizConstant.KEY_PARAMS, params);
        return result.toString();
    }

    public static final String requestNewNoticeMsg(int startId, int startNo,
            int max,String userId) throws JSONException {
        JSONObject result = new JSONObject();
        result.put(NSBizConstant.KEY_VERSION, MESSAGE_VERSION);
        result.put(NSBizConstant.KEY_CMD, CMD_NOTICE_GET_NEW);

        // params
        JSONObject params = new JSONObject();
        params.put(NSBizConstant.KEY_COMM_START_ID, startId);
        params.put(NSBizConstant.KEY_COMM_START_NO, startNo);
        params.put(NSBizConstant.KEY_COMM_MAX, max);
        params.put(NSBizConstant.KEY_USER_ID, userId);
        result.put(NSBizConstant.KEY_PARAMS, params);
        return result.toString();
    }

    public static final String requestNewNoticeListMsg(String userId) throws JSONException {
        JSONObject result = new JSONObject();
        result.put(NSBizConstant.KEY_VERSION, MESSAGE_VERSION);
        result.put(NSBizConstant.KEY_CMD, CMD_PUSH_GET_NOTICE_LIST);

        // params
        JSONObject params = new JSONObject();
        params.put(NSBizConstant.KEY_USER_ID, userId);
        result.put(NSBizConstant.KEY_PARAMS, params);
        return result.toString();
    }

    public static final String requestNewNoticeSizeMsg(String userId) throws JSONException {
        JSONObject result = new JSONObject();
        result.put(NSBizConstant.KEY_VERSION, MESSAGE_VERSION);
        result.put(NSBizConstant.KEY_CMD, CMD_PUSH_GET_NOTICE_SIZE);

        // params
        JSONObject params = new JSONObject();
        params.put(NSBizConstant.KEY_USER_ID, userId);
        result.put(NSBizConstant.KEY_PARAMS, params);
        return result.toString();
    }

    private static final JSONObject commonReqParam() throws JSONException {
        JSONObject result = new JSONObject();
        result.put(NSBizConstant.KEY_VERSION, MESSAGE_VERSION);
        result.put(NSBizConstant.KEY_CMD, CMD_VINE_SHOW);

        return result;
    }

    public static final String requestLoginMsg(String phone,String password) throws JSONException {
        JSONObject result = commonReqParam();

        // params
        JSONObject params = new JSONObject();
        params.put(NSBizConstant.KEY_PHONE, phone);
        params.put(NSBizConstant.KEY_PASSWORD, password);
        result.put(NSBizConstant.KEY_PARAMS, params);

        return result.toString();
    }

    public static final String reqSendSMSCodeMsg(String phone, int flag) throws JSONException {
        JSONObject result = commonReqParam();

        // params
        JSONObject params = new JSONObject();
        params.put(NSBizConstant.KEY_PHONE, phone);
        params.put(NSBizConstant.KEY_FORGET_FLAG, flag);
        result.put(NSBizConstant.KEY_PARAMS, params);

        return result.toString();
    }

    public static final String reqRegisterNextMsg(String phone, String smsCode) throws JSONException {
        JSONObject result = commonReqParam();

        // params
        JSONObject params = new JSONObject();
        params.put(NSBizConstant.KEY_PHONE, phone);
        params.put(NSBizConstant.KEY_SMS_CODE, smsCode);
        result.put(NSBizConstant.KEY_PARAMS, params);

        return result.toString();
    }

    public static final String reqRegisterMsg(String phone,String password, String nickname) throws JSONException {
        JSONObject result = commonReqParam();

        // params
        JSONObject params = new JSONObject();
        params.put(NSBizConstant.KEY_PHONE, phone);
        params.put(NSBizConstant.KEY_PASSWORD, password);
        params.put(NSBizConstant.KEY_NICKNAME, nickname);
        result.put(NSBizConstant.KEY_PARAMS, params);

        return result.toString();
    }

    public static final String reqPasswordResetMsg(String phone,String password) throws JSONException {
        JSONObject result = commonReqParam();

        // params
        JSONObject params = new JSONObject();
        params.put(NSBizConstant.KEY_PHONE, phone);
        params.put(NSBizConstant.KEY_PASSWORD, password);
        result.put(NSBizConstant.KEY_PARAMS, params);

        return result.toString();
    }

    public static final String reqGetUserMsg(String userId, String password) throws JSONException {
        JSONObject result = commonReqParam();

        // params
        JSONObject params = new JSONObject();
        params.put(NSBizConstant.KEY_USER_ID, userId);
        params.put(NSBizConstant.KEY_PASSWORD, password);
        result.put(NSBizConstant.KEY_PARAMS, params);

        return result.toString();
    }

    public static final String reqSetSexMsg(String userId,String sex) throws JSONException {
        JSONObject result = commonReqParam();

        // params
        JSONObject params = new JSONObject();
        params.put(NSBizConstant.KEY_USER_ID, userId);
        params.put(NSBizConstant.KEY_SEX, sex);
        result.put(NSBizConstant.KEY_PARAMS, params);

        return result.toString();
    }

    public static final String reqModifyNicknameMsg(String userId,String nickname) throws JSONException {
        JSONObject result = commonReqParam();

        // params
        JSONObject params = new JSONObject();
        params.put(NSBizConstant.KEY_USER_ID, userId);
        params.put(NSBizConstant.KEY_NICKNAME, nickname);
        result.put(NSBizConstant.KEY_PARAMS, params);

        return result.toString();
    }

    public static final String reqModifySignMsg(String userId,String sign) throws JSONException {
        JSONObject result = commonReqParam();

        // params
        JSONObject params = new JSONObject();
        params.put(NSBizConstant.KEY_USER_ID, userId);
        params.put(NSBizConstant.KEY_SIGN, sign);
        result.put(NSBizConstant.KEY_PARAMS, params);

        return result.toString();
    }

    public static final String reqModifyPwdMsg(String userId,String oldPwd,String password) throws JSONException {
        JSONObject result = commonReqParam();

        // params
        JSONObject params = new JSONObject();
        params.put(NSBizConstant.KEY_USER_ID, userId);
        params.put(NSBizConstant.KEY_OLD_PASSWORD, oldPwd);
        params.put(NSBizConstant.KEY_PASSWORD, password);
        result.put(NSBizConstant.KEY_PARAMS, params);

        return result.toString();
    }

    public static final String reqBindNewPhoneMsg(String userId,String phone,String smsCode) throws JSONException {
        JSONObject result = commonReqParam();

        // params
        JSONObject params = new JSONObject();
        params.put(NSBizConstant.KEY_USER_ID, userId);
        params.put(NSBizConstant.KEY_PHONE, phone);
        params.put(NSBizConstant.KEY_SMS_CODE, smsCode);
        result.put(NSBizConstant.KEY_PARAMS, params);

        return result.toString();
    }

    public static final String reqCheckBindPhoneMsg(String userId,String bindPhone) throws JSONException {
        JSONObject result = commonReqParam();

        // params
        JSONObject params = new JSONObject();
        params.put(NSBizConstant.KEY_USER_ID, userId);
        params.put(NSBizConstant.KEY_PHONE, bindPhone);
        result.put(NSBizConstant.KEY_PARAMS, params);

        return result.toString();
    }


    // lixiaokui add
    public static final String GetDayDuxin(String argDay) throws JSONException {
        JSONObject result = new JSONObject();
        result.put(NSBizConstant.KEY_VERSION, MESSAGE_VERSION);
        result.put(NSBizConstant.KEY_CMD, CMD_VINE_SHOW);

        JSONObject params = new JSONObject();
        params.put(NSBizConstant.KEY_YMD_DUANXIN, argDay);
        result.put(NSBizConstant.KEY_PARAMS, params);
        return result.toString();
    }

    public static final String reqBindSinaMsg(String userId, String sinaUid, String sinaAccessToken, String sinaExpiresIn) throws JSONException {
        JSONObject result = commonReqParam();

        // params
        JSONObject params = new JSONObject();
        params.put(NSBizConstant.KEY_USER_ID, userId);
        params.put(NSBizConstant.KEY_SINA_UID, sinaUid);
        params.put(NSBizConstant.KEY_SINA_ACCESS_TOKEN, sinaAccessToken);
        params.put(NSBizConstant.KEY_SINA_EXPIRES_IN, sinaExpiresIn);
        result.put(NSBizConstant.KEY_PARAMS, params);

        return result.toString();
    }
}
