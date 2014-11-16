package com.infodeliver.client.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.infodeliver.client.base.NSServiceBase;
import com.infodeliver.client.utils.NSBizConstant;
import com.infodeliver.client.utils.NSMsgFactory;
import com.infodeliver.db.domain.NoticeBizBean;
import com.infodeliver.io.NSHttpManager;
import com.infodeliver.utils.NSAppConfig;
import com.infodeliver.utils.NSErrors;
import com.infodeliver.utils.NSLog;
import com.infodeliver.utils.NSResourceManager;
import com.infodeliver.utils.NSSharePreferenceKeeper;

/**
 * Push service. In fact, it is not a push service in the traditional sense.
 * This service pull messages from server with defined interval.
 * 
 * @author yujian
 * @version 1.0
 * @createDate 2014-8-5
 * @lastUpdate 2014-8-5
 */
public class NSPushService extends NSServiceBase {

	private ArrayList<NoticeBizBean> mNoticeListFromWeb;

	public int mDealHour;
	public int mDealDate;

	private static class PushMessage {
		public int noticeId;
		public String noticeCreatorId;
		public String title;
		public String content;

		public static PushMessage initPushMessage() {
			PushMessage item = new PushMessage();
			item.noticeId = -1;
			item.noticeCreatorId = "";
			item.title = "益宝宝";
			item.content = "有新通知";
			return item;
		}

		public static PushMessage initPushMessageFromJson(
				NoticeBizBean noticeBean) {
			PushMessage item = new PushMessage();
			item.noticeId = noticeBean.getId();
			item.noticeCreatorId = noticeBean.getUserId();
			item.title = noticeBean.getSubjectText();
			item.content = noticeBean.getText();
			return item;
		}
	}

	private static final int MINUTE_IN_HOUR = 60;
	private static final int HOURS_IN_DAY = 24;
	private static final int RANDOM = 10;
	private static final int MINUTE_DEAL = MINUTE_IN_HOUR - RANDOM;

	private static final int PUSH_TIME_NEW_DAY = 0;

	private static final int PUSH_MSG_PULL_STOP = 0;
	private static final int PUSH_MSG_PULL_CONTINUE = 1;

	private static final int MAX_NOTIFICATION_ID = 1000;
	private int mPendingIntentCount = 1;
	private static final String TAG = "NSPushService";

	private ArrayList<PushMessage> mPushMessages = null;
	private NotificationManager mNotificationManager = null;
	private int mNotificationId = 0;
	private static final String NS_PUSH_ACTION = "com.infodeliver.client.broadcast.action.NS_PUSH_ACTION";

	private int mDealMinute = -1;

	private BroadcastReceiver mPushReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			NSLog.i("NSPushService>>>onReceive");
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			int minute = calendar.get(Calendar.MINUTE);
			NSLog.i(TAG, "minute: " + minute);
			if (MINUTE_DEAL == minute) {
				Random random = new Random();
				int tempRandom = random.nextInt(RANDOM * 2);

				mDealMinute = (tempRandom + MINUTE_DEAL) % MINUTE_IN_HOUR;
				calendar.add(Calendar.MINUTE, RANDOM);
				mDealHour = calendar.get(Calendar.HOUR_OF_DAY);
				mDealDate = calendar.get(Calendar.DAY_OF_MONTH);
				NSLog.d("mDealMinute:" + mDealMinute);
				NSLog.d("mDealHour:" + mDealHour);
				NSLog.d("mDealDate:" + mDealDate);
			}
			NSLog.i(TAG, "mDealMinute: " + mDealMinute);
			if (minute == mDealMinute) {
				pushCheck();
				mDealMinute = -1;
			}

			// pushCheck();
		}
	};

	@Override
	public void onCreate() {
		super.onCreate();
		NSLog.i("NSPushService>>>onCreate");
		mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		mPushMessages = new ArrayList<PushMessage>();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_TIME_TICK);
		filter.addAction(Intent.ACTION_TIME_CHANGED);
		filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
		filter.addAction(NS_PUSH_ACTION);
		registerReceiver(mPushReceiver, filter);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		NSLog.i("NSPushService>>>onStart");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		NSLog.i("NSPushService>>>onStartCommand");
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		NSLog.i("onDestroy");
		if (null != mPushReceiver) {
			unregisterReceiver(mPushReceiver);
			mPushReceiver = null;
		}
		mPushMessages = null;
		mNotificationManager = null;
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private void pushCheck() {
		NSLog.i("pushCheck");

		// check share flag
		// requestNewNoticeSize();
		requestNewNoticeList();

	}

	private PendingIntent getNewPendingIntent(PushMessage pushMessage) {
		NSLog.i("getNewPendingIntent");
		Intent intent = new Intent();
		intent.putExtra(NSBizConstant.KEY_NOTICES_ID, pushMessage.noticeId);
		intent.putExtra(NSBizConstant.KEY_NOTICES_CREATOR_ID,
				pushMessage.noticeCreatorId);
		intent.setAction("com.infodeliver.push.receiver");
		// return PendingIntent.getBroadcast(this, mPendingIntentCount, intent,
		// PendingIntent.FLAG_UPDATE_CURRENT);
		if (mPendingIntentCount == MAX_NOTIFICATION_ID) {
			mPendingIntentCount = 1;
		}
		return PendingIntent.getBroadcast(this, mPendingIntentCount++, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
	}

	private void sendNotifications() {
		NSLog.i("sendNotifications");
		if (mPushMessages == null || mPushMessages.size() == 0) {
			return;
		}

		for (int i = 0; i < mPushMessages.size(); i++) {
			PushMessage msg = mPushMessages.get(i);
			Notification notification = new Notification(
					NSResourceManager.getResourceID("ic_icon_push", "drawable"),
					msg.content, System.currentTimeMillis());
			notification.setLatestEventInfo(getApplicationContext(), msg.title,
					msg.content, getNewPendingIntent(msg));
			notification.flags |= Notification.FLAG_AUTO_CANCEL;
			notification.defaults |= Notification.DEFAULT_SOUND;
			mNotificationId++;
			mNotificationId = mNotificationId % MAX_NOTIFICATION_ID;
			mNotificationManager.notify(mNotificationId, notification);
		}
		mPushMessages.clear();
	}

	private void requestNewNoticeSize() {
		NSLog.i("requestNewNoticeSize");
		// get user id
		String userId = NSSharePreferenceKeeper.getStringValue(this,
				NSBizConstant.SPK_USER_ID, false);
		try {
			String msg = NSMsgFactory.requestNewNoticeSizeMsg(userId);
			postMessage(NSBizConstant.REQUEST_PUSH_NOTICE_SIZE,
					NSAppConfig.SERVER_URL
							+ NSMsgFactory.CMD_PUSH_GET_NOTICE_SIZE,
					NSHttpManager.POST, msg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void requestNewNoticeList() {
		NSLog.i("requestNewNoticeList");
		// get user id
		String userId = NSSharePreferenceKeeper.getStringValue(this,
				NSBizConstant.SPK_USER_ID, false);
		try {
			String msg = NSMsgFactory.requestNewNoticeListMsg(userId);
			postMessage(NSBizConstant.REQUEST_PUSH_NOTICE_LIST,
					NSAppConfig.SERVER_URL
							+ NSMsgFactory.CMD_PUSH_GET_NOTICE_LIST,
					NSHttpManager.POST, msg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onResult(int requestID, String result) {
		NSLog.i("NSPushService>>>onResult");
		try {
			JSONObject params = getParamsFromResp(requestID, result);
			switch (requestID) {
			case NSBizConstant.REQUEST_PUSH_NOTICE_SIZE:
				if (null == params) {
					return;
				}

				int pushNoticeSize = params
						.getInt(NSBizConstant.KEY_GET_PUSH_NOTICE_SIZE);
				NSLog.d("pushNoticeSize:" + pushNoticeSize);
				if (pushNoticeSize > 0) {
					onTitlePullShow();
				} else {
					return;
				}
				break;
			case NSBizConstant.REQUEST_PUSH_NOTICE_LIST:
				if (null == params) {
					return;
				}

				JSONArray tempArray = params
						.getJSONArray(NSBizConstant.KEY_GET_PUSH_NOTICE_LIST);
				Gson gson = new Gson();
				mNoticeListFromWeb = gson.fromJson(tempArray.toString(),
						new TypeToken<List<NoticeBizBean>>() {
						}.getType());
				NSLog.d("pushNoticeList.size():" + mNoticeListFromWeb.size());
				if (mNoticeListFromWeb.size() > 0) {
					onTitlePullShowList();
				} else {
					return;
				}
				break;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			onError(requestID, NSErrors.ERROR_RESPONSE_FORMAT);
		}
	}

	private void onTitlePullShow() {
		NSLog.i("onTitlePullShow");
		mPushMessages.clear();
		mPushMessages.add(PushMessage.initPushMessage());
		sendNotifications();
	}

	private void onTitlePullShowList() {
		NSLog.i("onTitlePullShow");
		mPushMessages.clear();
		for (int i = 0; i < mNoticeListFromWeb.size(); i++) {
			mPushMessages.add(PushMessage
					.initPushMessageFromJson(mNoticeListFromWeb.get(i)));
		}
		sendNotifications();
	}

	@Override
	public void onError(int requestID, String errorCode, String errorDesc) {
		NSLog.i("NSPushService>>>onError");

	}
}
