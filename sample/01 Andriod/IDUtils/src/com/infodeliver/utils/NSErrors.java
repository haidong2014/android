package com.infodeliver.utils;

import android.content.Context;

/**
 * Local errors in application.
 * 
 * @author yujian
 * @version 1.0
 * @createDate 2014-06-23
 * @lastUpdate 2014-06-23
 */
public class NSErrors {
    public static final String SUCCESS = "00";
    public static final String ERROR_LOGIN_TIMEOUT = "99";
    public static final String ERROR_KICK_OUT = "90";

    public static final String ERROR_LOCAL_BEGIN = "10000";
    public static final String ERROR_NETWORK = "10001";
    public static final String ERROR_RESPONSE_CODE = "10002";
    public static final String ERROR_RESPONSE_FORMAT = "10003";
    public static final String ERROR_ENCRYPTION_KEY_TIMEOUT = "10004";
    public static final String ERROR_STORAGE_NOT_ENOUGHT = "10005";
    public static final String ERROR_ABILITY_CHECK_FAIL = "10006";
    public static final String ERROR_DOWNLOAD_FILE = "10007";
    public static final String ERROR_NO_AREA = "10008";
    public static final String ERROR_LOCATING_TIMEOUT = "10009";

    public static final String ERROR_NO_BENEFIT = "64";
    public static final String ERROR_GET_SESSION_KEY_FAIL = "1000";

    /**
     * Get error detail message name by error id. The message name can be used
     * in {@link UPStrings#get(String name)}
     * 
     * @param context
     * @param error The ID of the desired error.
     * @return <b>String</b> The associated detail message String name.
     */
    public static String getLocalErrorMsg(Context context, String error) {
        return getLocalErrorMsgId(error);
    }

    private static String getLocalErrorMsgId(String error) {
        if (ERROR_NETWORK.equals(error)) {
            return "error_network";
        } else if (ERROR_RESPONSE_FORMAT.equals(error)) {
            return "error_server";
        } else if (ERROR_ENCRYPTION_KEY_TIMEOUT.equals(error)) {
            return "error_key_time_out";
        } else if (ERROR_STORAGE_NOT_ENOUGHT.equals(error)) {
            return "error_storage_not_enough";
        } else if (ERROR_ABILITY_CHECK_FAIL.equals(error)) {
            return "error_ability_check_fail";
        } else if (ERROR_DOWNLOAD_FILE.equals(error)) {
            return "error_download_file";
        } else if (ERROR_NO_AREA.equals(error)) {
            return "error_no_area";
        } else if (ERROR_LOCATING_TIMEOUT.equals(error)) {
            return "error_locating_timeout";
        } else if (ERROR_GET_SESSION_KEY_FAIL.equals(error)) {
            return "error_operation_fail";
        } else {
            return "error_unrecognized";
        }
    }
}
