package com.infodeliver.client.service;

import com.infodeliver.client.edu.MainTabActivity;
import com.infodeliver.client.utils.NSBizConstant;
import com.infodeliver.utils.NSLog;
import com.infodeliver.utils.NSSharePreferenceKeeper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NSAlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		NSLog.i("NSAlarmReceiver>>>onReceive");

		int noticeId = intent.getIntExtra(NSBizConstant.KEY_NOTICES_ID, -1);
		NSLog.d("noticeId:" + noticeId);
		if (-1 != noticeId) {
			NSSharePreferenceKeeper.keepBooleanValue(context,
					NSBizConstant.SPK_PUSH_ALARM, true);
			NSLog.d("NSAlarmReceiver>>>noticeId:"
					+ intent.getIntExtra(NSBizConstant.KEY_NOTICES_ID, -1));
			NSLog.d("NSAlarmReceiver>>>noticeCreatorId"
					+ intent.getStringExtra(NSBizConstant.KEY_NOTICES_CREATOR_ID));
			NSSharePreferenceKeeper.keepIntValue(context,
					NSBizConstant.SPK_PUSH_NOTICE_ID,
					intent.getIntExtra(NSBizConstant.KEY_NOTICES_ID, -1));
			NSSharePreferenceKeeper
					.keepStringValue(
							context,
							NSBizConstant.SPK_PUSH_NOTICE_CREATOR_ID,
							intent.getStringExtra(NSBizConstant.KEY_NOTICES_CREATOR_ID));
			Intent activityIntent = new Intent(context, MainTabActivity.class);
			activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(activityIntent);
		}

	}

}
