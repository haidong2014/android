package com.infodeliver.client.service;

import com.infodeliver.utils.NSLog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Used to receive {@link Intent#ACTION_BOOT_COMPLETED} broadcast. The receiver
 * will start {@link UPPushService} while received the broadcast.
 * 
 * @author yujian
 * @version 1.0
 * @createDate 2014-8-5
 * @lastUpdate 2014-8-5
 */
public class NSBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
    	NSLog.i("NSBootReceiver>>>onReceive");
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            context.startService(new Intent(context, NSPushService.class));
        }
    }
}
