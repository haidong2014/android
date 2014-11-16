package com.infodeliver.client.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.infodeliver.client.edu.MainTabActivity;
import com.infodeliver.client.utils.NSBizConstant;
import com.infodeliver.db.domain.NoticeBizBean;
import com.infodeliver.utils.NSLog;
import com.infodeliver.utils.NSSharePreferenceKeeper;

/**
 * 
 * @author yujian
 * @version 1.0
 * @createDate 2014-8-5
 * @lastUpdate 2014-8-5
 */
public class NSPushReceiver extends BroadcastReceiver {
	protected static final String ORDER_TYPE_AUTO = "01";

	protected static final int JUMP_TO_MAIN = 1;
	protected static final int JUMP_TO_PAY = 2;
	protected static final int JUMP_TO_APP = 3;

	protected static final int FROM_ORDER = 2;
	protected static final int FROM_REMIND = 3;

	@Override
	public void onReceive(Context context, Intent intent) {
		NSLog.i("NSPushReceiver>>>onReceive");

		NSSharePreferenceKeeper.keepBooleanValue(context,
				NSBizConstant.SPK_PUSH_NEED_RESUME, true);
		NSSharePreferenceKeeper.keepIntValue(context,
				NSBizConstant.SPK_PUSH_NOTICE_ID,
				intent.getIntExtra(NSBizConstant.KEY_NOTICES_ID, -1));
		NSSharePreferenceKeeper.keepStringValue(context,
				NSBizConstant.SPK_PUSH_NOTICE_CREATOR_ID,
				intent.getStringExtra(NSBizConstant.KEY_NOTICES_CREATOR_ID));
		Intent newActivityIntent = new Intent(context, MainTabActivity.class);
		newActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(newActivityIntent);

	}

}
