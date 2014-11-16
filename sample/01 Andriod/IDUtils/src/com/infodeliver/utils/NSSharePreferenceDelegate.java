package com.infodeliver.utils;

import android.annotation.SuppressLint;
import android.content.SharedPreferences.Editor;
/**
 * @author yujian
 * @version 1.0
 * @createDate 2014-06-23
 * @lastUpdate 2014-06-23
 */
public class NSSharePreferenceDelegate {
    @SuppressLint("NewApi")
    public static final void commit(Editor editor) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.GINGERBREAD) {
            editor.commit();
        } else {
            editor.apply();
        }
    }
}
