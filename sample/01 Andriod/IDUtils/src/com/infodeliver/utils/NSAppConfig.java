package com.infodeliver.utils;

import com.infodeliver.utils.NSUtils;

/**
 * Global configuration for application.
 *
 * @author yujian
 * @version 1.0
 * @createDate 2014-06-23
 * @lastUpdate 2014-06-23
 */
public class NSAppConfig {

    public static final String SERVER_URL = "http://10.10.52.10:8080/app/services/";

    public static final boolean CRASH_LOG_TO_FILE = true;

    public static final boolean LOG_TO_FILE = true;

    public static final boolean DEBUG = true;

    public static final String APP_LOG_DIR = NSUtils
            .getWorkFolder(NSUtils.PATH_SDCARD) + "log/";

    public static final String APP_DOWNLOAD_DIR = NSUtils
            .getWorkFolder(NSUtils.PATH_SDCARD) + "download/";

    public static final String APP_CRASH_LOG_DIR = APP_LOG_DIR + "crash/";

    public static final String SERVER_URL_GET_DAY_DUANXIN = "http://10.10.52.18:8080/app/services/notify_getday";
    public static final String SERVER_URL_GET_ICONS = "http://10.10.52.18:8080/app/services/notify_getIcons";
}
