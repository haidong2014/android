package com.infodeliver.client.utils;

/**
 * Biz constant
 *
 * @author yujian
 * @version 1.0
 * @createDate 2014-06-23
 * @lastUpdate 2014-06-23
 */
public class NSBizConstant {

    // --------------------------------------------------------------------------
    // KEY
    // json
    public static final String KEY_ID = "id";
    public static final String KEY_VERSION = "v";
    public static final String KEY_CMD = "cmd";
    public static final String KEY_PARAMS = "params";

    // key commom
    public static final String KEY_COMM_CREATE_ID = "create_id";
    public static final String KEY_COMM_START_ID = "start_id";
    public static final String KEY_COMM_START_NO = "start_no";
    public static final String KEY_COMM_MAX = "max";
    public static final String KEY_COMM_BEAN = "bean";
    public static final String KEY_COMM_NEED_UPDATE_LIST_FLAG = "need_update_list_flag";

    // vine
    public static final String KEY_VINE_TOP_SHOW = "top_show";
    public static final String KEY_VINE_USER_ICON = "user_icon";
    public static final String KEY_VINE_USER_NAME = "user_name";

    // person
    public static final String KEY_PERSON_ID = "_id";
    public static final String KEY_PERSON_NAME = "name";
    public static final String KEY_PERSON_AGE = "age";

    // user
    public static final String KEY_USER_NAME = "user_name";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_OLD_PASSWORD = "old_password";

    public static final String KEY_PHONE = "phone";
    public static final String KEY_SMS_CODE = "sms_code";
    public static final String KEY_FORGET_FLAG = "forget_flag";

    public static final String KEY_NICKNAME = "nickname";
    public static final String KEY_SEX = "sex";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_SIGN = "sign";
    public static final String KEY_SINA_UID = "sina_uid";
    public static final String KEY_SINA_ACCESS_TOKEN = "sina_access_token";
    public static final String KEY_SINA_EXPIRES_IN = "sina_expires_in";

    // diary_blog
    public static final String DB_BLOG_TYPE = "blog_type";
    public static final String DB_MEMO = "memo";
    public static final String DB_VISIBLE = "visible";

    //login
    public static final String FORGET_FLAG = "FORGET_FLAG";
    public static final String PHONE = "PHONE";
    public static final String SINA_USER = "SINA_USER";

    // list
    public static final String KEY_CHECKED_COUNT = "checked_count";
    public static final String KEY_CHECKED_ARRAY_STR = "checked_array";

    // notice save
    public static final String KEY_NOTICE_CATEGORY_ID = "category_id";
    public static final String KEY_NOTICE_SUBJECT = "subject";
    public static final String KEY_NOTICE_SUBJECT_IMAGE_ID = "subject_image_id";
    public static final String KEY_NOTICE_TEXT = "text";
    public static final String KEY_NOTICE_IMPORTANCE = "importance";
    public static final String KEY_NOTICE_SEND_DATE = "send_date";
    public static final String KEY_NOTICE_REPLY_FLAG = "reply_flag";
    public static final String KEY_NOTICE_RECIPIENT_ARRAY = "recipient_array";
    public static final String KEY_NOTICE_RECIPIENT_TYPE = "recipient_type";
    
    // reply
    public static final String KEY_REPLY_LIST = "reply_list";
    public static final String KEY_REPLY_TYPE = "reply_type";
    public static final String KEY_REPLY_TEXT = "reply_text";

    // notice get
    public static final String KEY_NOTICES = "notices";
    public static final String KEY_NOTICES_MORE = "notices_more";
    public static final String KEY_NOTICES_RECIPIENT_ALL = "recipient_all";
    public static final String KEY_NOTICES_RECIPIENT_GET_ALL = "get_all";
    public static final String KEY_NOTICES_ID = "notice_id";
    public static final String KEY_NOTICES_CREATOR_ID = "notice_creator_id";
    public static final String KEY_NOTICE_BEAN = "notice_bean";

    // group
    public static final String KEY_GROUP = "group_list";
    public static final String KEY_GROUP_ID = "group_id";
    public static final String KEY_GROUP_MEMBER = "group_member_list";
    
    // recipient
    public static final String KEY_RECIPIENT_LIST = "recipient_list";
    public static final String KEY_NEED_CHANGE_RECIPIENT_READ_STATUS_FLAG = "need_change_recipient_status";

    // push
    public static final String KEY_GET_PUSH_NOTICE_SIZE = "new_notice_size";
    public static final String KEY_GET_PUSH_NOTICE_LIST = "push_notice_list";
    public static final String KEY_GET_PUSH_NOTICE_BEAN = "push_notice_bean";
    public static final String KEY_PUSH_NOTICES_ID = "push_notice_id";
    public static final String KEY_PUSH_NOTICES_CREATOR_ID = "push_notice_creator_id";

    // --------------------------------------------------------------------------
    // REQUEST
    public static final int REQUEST_START = 0;
    public static final int REQUEST_WEB_HTTP_START = 1000;
    public static final int REQUEST_DOWNLOAD_START = 10000;

    public static final int REQUEST_INIT = REQUEST_START + 1;
    public static final int REQUEST_QUERY_VINE_LIST = REQUEST_INIT + 1;
    public static final int REQUEST_QUERY_DIARY_LIST = REQUEST_QUERY_VINE_LIST + 1;

    // request notice
    public static final int REQUEST_DELETE_NOTICE = REQUEST_QUERY_DIARY_LIST + 1;
    public static final int REQUEST_SAVE_NOTICE = REQUEST_DELETE_NOTICE + 1;
    public static final int REQUEST_EDIT_NOTICE = REQUEST_SAVE_NOTICE + 1;
    public static final int REQUEST_GET_NOTICE = REQUEST_EDIT_NOTICE + 1;
    public static final int REQUEST_GET_NOTICE_MORE = REQUEST_GET_NOTICE + 1;
    public static final int REQUEST_GET_NOTICE_NEW = REQUEST_GET_NOTICE_MORE + 1;
    public static final int REQUEST_GET_NOTICE_WITH_RECIPIENT_STATUS = REQUEST_GET_NOTICE_NEW + 1;
    public static final int REQUEST_GET_NOTICE_WITH_RECIPIENT_STATUS_UPDATE = REQUEST_GET_NOTICE_WITH_RECIPIENT_STATUS + 1;
    public static final int REQUEST_GET_RECIPIENT_ID = REQUEST_GET_NOTICE_WITH_RECIPIENT_STATUS_UPDATE + 1;
    public static final int REQUEST_UPDATE_RECIPIENT_STATUS = REQUEST_GET_RECIPIENT_ID + 1;
    
    // group
    public static final int REQUEST_GET_GROUP_LIST = REQUEST_UPDATE_RECIPIENT_STATUS + 1;
    public static final int REQUEST_GET_GROUP_MENBER_LIST = REQUEST_GET_GROUP_LIST + 1;
    
    // reply
    public static final int REQUEST_SAVE_REPLY = REQUEST_GET_GROUP_MENBER_LIST + 1;
    public static final int REQUEST_GET_REPLY_LIST = REQUEST_SAVE_REPLY + 1;

    // my account
    public static final int REQUEST_GET_USER = REQUEST_GET_REPLY_LIST + 1;
    public static final int REQUEST_SET_SEX = REQUEST_GET_USER + 1;
    public static final int REQUEST_MODIFY_NICKNAME = REQUEST_SET_SEX + 1;
    public static final int REQUEST_MODIFY_SIGN = REQUEST_MODIFY_NICKNAME + 1;
    public static final int REQUEST_MODIFY_PWD = REQUEST_MODIFY_SIGN + 1;
    public static final int REQUEST_BIND_NEW_PHONE = REQUEST_MODIFY_PWD + 1;
    public static final int REQUEST_CHECK_BIND_PHONE = REQUEST_BIND_NEW_PHONE + 1;
    public static final int REQUEST_BIND_SINA = REQUEST_CHECK_BIND_PHONE + 1;
    public static final int REQUEST_UNBIND_SINA = REQUEST_BIND_SINA + 1;

    // recipient
    public static final int REQUEST_RECIPIENT_LIST = REQUEST_UNBIND_SINA + 1;

    // push
    public static final int REQUEST_PUSH_NOTICE_LIST = REQUEST_RECIPIENT_LIST + 1;
    public static final int REQUEST_PUSH_NOTICE_SIZE = REQUEST_PUSH_NOTICE_LIST + 1;

    // login
    public static final int REQUEST_LOGIN = REQUEST_PUSH_NOTICE_SIZE + 1;
    public static final int REQUEST_SEND_SMS_CODE = REQUEST_LOGIN + 1;
    public static final int REQUEST_CHECK_SMS_CODE = REQUEST_SEND_SMS_CODE + 1;
    public static final int REQUEST_REGISTER = REQUEST_CHECK_SMS_CODE + 1;
    public static final int REQUEST_PASSWORD_RESET = REQUEST_REGISTER + 1;
    

    // --------------------------------------------------------------------------
    // SharePreferenceKeeper
    public static final String SPK_ALREADY_LOGIN = "already_login";
    public static final String SPK_USER_ID = "user_id";
    public static final String SPK_USER_NAME = "user_name";
    public static final String SPK_USER_PASS = "user_pass";
    public static final String SPK_USER_SEX = "user_sex";
    public static final String SPK_USER_SIGN = "user_sign";
    public static final String SPK_USER_TEL = "user_tel";
    public static final String SPK_RECEIVE_NEW_ALERT = "receive_new_alert";
    public static final String SPK_PUSH_NEED_RESUME = "need_resume";
    public static final String SPK_PUSH_ALARM = "push_alarm";
    public static final String SPK_PUSH_NEED_JUMP = "push_need_jump";
    public static final String SPK_PUSH_NOTICE_ID = "push_notice_id";
    public static final String SPK_TOP_NOTICE_ID = "top_notice_id";
    public static final String SPK_PUSH_NOTICE_CREATOR_ID = "push_notice_creator_id";

    // --------------------------------------------------------------------------
    // FROM FLAG
    public static final String FROM_FLAG = "from_flag";
    public static final String FROM_FLAG_MAIN_TAB = "main_tab";
    public static final String FROM_FLAG_NOTICE = "notice";

    // --------------------------------------------------------------------------
    // db common
    public static final String DB_ID = "id";
    public static final String DB_GID = "gid";
    public static final String DB_USER_ID = "user_id";
    public static final String DB_CREATE_TIME = "create_time";
    public static final String DB_DELETE_FLAG = "delete_flag";
    public static final String DB_UPDATE_TIME = "update_time";


    // lixiao add
    public static final String KEY_YMD_DUANXIN = "dunxin_day";
    public static final String KEY_DUANXIN_DB = "db";
    public static final int REQUEST_GET_DAY_DUANXIN = REQUEST_START + 101;
    public static final int REQUEST_GET_ICONS = REQUEST_START + 102;

    public static final String SERVER_URL_GET_DAY_DUANXIN = "http://10.10.52.18:8080/app/services/notify_getday";
    public static final String SERVER_URL_GET_ICONS = "http://10.10.52.18:8080/app/services/notify_getIcons";



}
