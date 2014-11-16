package com.infodeliver.utils;

import java.io.File;

import android.annotation.SuppressLint;
import android.util.Log;

/**
 * Log util used for application.
 * 
 * @author yujian
 * @version 1.0
 * @createDate 2014-06-23
 * @lastUpdate 2014-06-23
 */
@SuppressLint("SimpleDateFormat")
public class NSLog {

    private static final String TAG = "infodeliver";
    public static final String LOG_FILE_PREFIX = "infodeliver_log";
    public static final String CRASH_FILE_PREFIX = "infodeliver_crash";

    public static final int LOG_FILE_SIZE = 1024 * 1024 * 2;
    public static final int LOG_FILE_MAX_COUNT = 5;

    public static final int FILE_TYPE_LOG = 0;
    public static final int FILE_TYPE_CRASH = 1;

    public static File sCurrentLogFile = null;
    public static File sCurrentCrashFile = null;

    public static final void d(String tag, String msg) {
        doLog(Log.DEBUG, tag, msg, null);
    }

    public static final void v(String tag, String msg) {
        doLog(Log.VERBOSE, tag, msg, null);
    }

    public static final void i(String tag, String msg) {
        doLog(Log.INFO, tag, msg, null);
    }

    public static final void e(String tag, String msg) {
        doLog(Log.ERROR, tag, msg, null);
    }

    public static final void w(String tag, String msg) {
        doLog(Log.WARN, tag, msg, null);
    }

    public static final void d(String tag, String msg, Throwable ex) {
        doLog(Log.DEBUG, tag, msg, ex);
    }

    public static final void v(String tag, String msg, Throwable ex) {
        doLog(Log.VERBOSE, tag, msg, ex);
    }

    public static final void i(String tag, String msg, Throwable ex) {
        doLog(Log.INFO, tag, msg, ex);
    }

    public static final void e(String tag, String msg, Throwable ex) {
        doLog(Log.ERROR, tag, msg, ex);
    }

    public static final void w(String tag, String msg, Throwable ex) {
        doLog(Log.WARN, tag, msg, ex);
    }

    public static final void d(String msg) {
        d(TAG, msg, null);
    }

    public static final void v(String msg) {
        v(TAG, msg, null);
    }

    public static final void i(String msg) {
        i(TAG, msg, null);
    }

    public static final void e(String msg) {
        e(TAG, msg, null);
    }

    public static final void w(String msg) {
        w(TAG, msg, null);
    }

    public static final void d(String msg, Throwable ex) {
        d(TAG, msg, ex);
    }

    public static final void v(String msg, Throwable ex) {
        v(TAG, msg, ex);
    }

    public static final void i(String msg, Throwable ex) {
        i(TAG, msg, ex);
    }

    public static final void e(String msg, Throwable ex) {
        e(TAG, msg, ex);
    }

    public static final void w(String msg, Throwable ex) {
        w(TAG, msg, ex);
    }

    private static final void doLog(int level, String tag, String msg,
            Throwable ex) {
        if (NSAppConfig.DEBUG) {
            switch (level) {
            case Log.VERBOSE:
                Log.v(tag, msg, ex);
                break;
            case Log.DEBUG:
                Log.d(tag, msg, ex);
                break;
            case Log.INFO:
                Log.i(tag, msg, ex);
                break;
            case Log.WARN:
                Log.w(tag, msg, ex);
                break;
            case Log.ERROR:
                Log.e(tag, msg, ex);
                break;
            default:
                break;
            }
        }

    }
}
