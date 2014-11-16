package com.infodeliver.client.base;

import android.app.Application;
import android.content.Intent;

import com.infodeliver.client.service.NSPushService;
import com.infodeliver.client.utils.NSBizConstant;
import com.infodeliver.utils.NSLog;
import com.infodeliver.utils.NSResourceManager;
import com.infodeliver.utils.NSSharePreferenceKeeper;

/**
 * Custom application.
 *
 * @author yujian
 * @version 1.0
 * @createDate 2014-06-23
 * @lastUpdate 2014-06-23
 */
public class NSApplication extends Application {

    private static final String TAG = "NSApplication";
    private boolean mmIsNeedDiaryRefresh = false;

    public boolean isMmDiaryRefresh() {
        return mmIsNeedDiaryRefresh;
    }

    public void setMmDiaryRefresh(boolean mmIsNeedDiaryRefresh) {
        this.mmIsNeedDiaryRefresh = mmIsNeedDiaryRefresh;
    }

    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public void onCreate() {
        NSLog.i(TAG, "onCreate");
        super.onCreate();
        NSResourceManager.init(getApplicationContext());

        initSP();
        //start service
        startService(new Intent(this, NSPushService.class));
        NSLog.i(TAG, "init end...");
    }
    
    private void initSP() {
    	NSSharePreferenceKeeper.keepBooleanValue(this,
				NSBizConstant.SPK_PUSH_NEED_RESUME, false);
		NSSharePreferenceKeeper.keepBooleanValue(this,
				NSBizConstant.SPK_PUSH_ALARM, false);
    }
}
