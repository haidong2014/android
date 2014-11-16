package com.infodeliver.client.edu.notice;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.infodeliver.client.base.NSActivityBase;
import com.infodeliver.client.edu.R;
import com.infodeliver.client.edu.list.RecipientReadListActivity;
import com.infodeliver.client.edu.reply.ReplyListActivity;
import com.infodeliver.client.service.NSAlarmReceiver;
import com.infodeliver.client.ui.ScreenInfo;
import com.infodeliver.client.ui.WheelMain;
import com.infodeliver.client.utils.NSBizConstant;
import com.infodeliver.client.utils.NSMsgFactory;
import com.infodeliver.db.biz.AlarmService;
import com.infodeliver.db.domain.AlarmBizBean;
import com.infodeliver.db.domain.NoticeBizBean;
import com.infodeliver.io.NSHttpManager;
import com.infodeliver.utils.NSAppConfig;
import com.infodeliver.utils.NSLog;
import com.infodeliver.utils.NSResourceManager;
import com.infodeliver.utils.NSSharePreferenceKeeper;
import com.infodeliver.utils.NSUtils;

/**
 * Notice detail
 * 
 * @author yujian
 * @version 1.0
 * @createDate 2014-07-22
 * @lastUpdate 2014-07-22
 */
public class NoticeDetailActivity extends NSActivityBase {
	private static final String TAG = "NoticeDetailActivity";

	private static final int FLAG_ACTIVITY_REQUEST_EDIT = 30;

	private static final String MORE_TYPE_DELETE = "delete";
	private static final String MORE_TYPE_GET_SENDER = "get_sender";
	private static final String MORE_TYPE_EDIT = "edit";

	private TextView mTitleLeftBtn;
	private TextView mTitleRightBtn;
	 private TextView mTitleName;

	private TextView mNoticeSubject;
	private EditText mNoticeText;
	private TextView mNoticeSendDay;
	private TextView mNoticeSendUserName;
	private ImageView mSubjectImage;

	private RelativeLayout mReceiveLayout;
	private RelativeLayout mReplyLayout;
	private RelativeLayout mDeleteLayout;
	private RelativeLayout mAlarmLayout;
	private RelativeLayout mSetTopLayout;

	private CheckBox mAlarmCb;
	private CheckBox mSetTopCb;

	private TextView mRecipientStatus;

	private String mUserId = "";

	private static final int DATE_MODE_FIX = 1;
	// true self false other
	private boolean mSendUserFlag = true;
	// true reply ok
	private boolean mReplyFlag = true;
	// need update read status flag
	private boolean mNeedChangeReadStatusFlag = false;
	// notice list need update
	private boolean mNoticeListUpdateFlag = false;
	// set alarm flage
	private boolean mNoticeAlarmFlag = false;

	private ArrayList<HashMap<String, String>> mMoreArray;

	private ArrayList<NoticeBizBean> mNoticeListFromWeb;
	private NoticeBizBean mNotice;
	private int mNoticeId;
	private String mNoticeCreatorId;

	private WheelMain wheelMain;
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm");

	private AlarmService mAlarmService = new AlarmService(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notice_detail);

		showLoadingView();

		mMoreArray = new ArrayList<HashMap<String, String>>();
		// user id from sp
		mUserId = NSSharePreferenceKeeper.getStringValue(this,
				NSBizConstant.SPK_USER_ID, false);
		NSLog.d("mUserId:" + mUserId);
		mNoticeId = getIntent().getIntExtra(NSBizConstant.KEY_NOTICES_ID, -1);
		mNoticeCreatorId = getIntent().getStringExtra(
				NSBizConstant.KEY_NOTICES_CREATOR_ID);
		int readCode = getIntent().getIntExtra(
				NSBizConstant.KEY_NEED_CHANGE_RECIPIENT_READ_STATUS_FLAG, 0);

		if (readCode == 0) {
			mNeedChangeReadStatusFlag = true;
		} else {
			mNeedChangeReadStatusFlag = false;
		}

		if (-1 == mNoticeId) {
			// to login or finish

		} else {
			if (mNoticeCreatorId.equals(mUserId)) {
				mSendUserFlag = true;
				getNoticeBeanByIdWidthRS();
			} else {
				mSendUserFlag = false;
				getNoticeBeanByIdWidthUpdate();
			}
		}

	}

	private void initMenu() {

		HashMap<String, String> getSenderMap = new HashMap<String, String>();
		getSenderMap.put(MORE_TYPE_GET_SENDER,
				getString(R.string.news_get_sender));
		mMoreArray.add(getSenderMap);

		HashMap<String, String> delteMap = new HashMap<String, String>();
		delteMap.put(MORE_TYPE_DELETE, getString(R.string.comm_btn_delete));
		mMoreArray.add(delteMap);
	}

	private void getNoticeBeanByIdWidthRS() {
		NSLog.i(TAG, "getNoticeBeanByIdWidthRS");
		String msg;
		try {
			msg = NSMsgFactory.requestNoticeWithRecipientStatusMsg(mNoticeId,
					mUserId);
			postMessage(
					NSBizConstant.REQUEST_GET_NOTICE_WITH_RECIPIENT_STATUS,
					NSAppConfig.SERVER_URL
							+ NSMsgFactory.CMD_NOTICE_GET_NOTICE_WITH_RECIPIENT_STATUS,
					NSHttpManager.POST, msg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void getNoticeBeanByIdWidthUpdate() {
		NSLog.i(TAG, "getNoticeBeanByIdWidthUpdate");
		String msg;
		try {
			msg = NSMsgFactory.requestNoticeWithRecipientStatusUpdateMsg(
					mNoticeId, mUserId, mNeedChangeReadStatusFlag);
			postMessage(
					NSBizConstant.REQUEST_GET_NOTICE_WITH_RECIPIENT_STATUS_UPDATE,
					NSAppConfig.SERVER_URL
							+ NSMsgFactory.CMD_NOTICE_GET_NOTICE_WITH_RECIPIENT_STATUS_UPDATE,
					NSHttpManager.POST, msg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void initTitle() {
		mTitleLeftBtn = (TextView) findViewById(R.id.title_left_btn);
		mTitleRightBtn = (TextView) findViewById(R.id.title_right_btn);
		mTitleRightBtn.setClickable(true);
		// mTitleRightBtn.setText(R.string.news_detail_btn_right_edit);
		mTitleName = (TextView) findViewById(R.id.title_name);
		mTitleName.setText(mNotice.getGroupName());
		mTitleLeftBtn.setClickable(true);
	}

	private void initView() {

		mNoticeSubject = (TextView) findViewById(R.id.notice_subject);
		mNoticeSubject.setText(mNotice.getSubjectText());
		mNoticeText = (EditText) findViewById(R.id.notice_text);
		mNoticeText.setText(mNotice.getText());
		mNoticeSendDay = (TextView) findViewById(R.id.notice_send_date);
		mNoticeSendDay.setText(mNotice.getSendDay());
		mNoticeSendUserName = (TextView) findViewById(R.id.notice_send_user_name);
		mNoticeSendUserName.setText(mNotice.getUserName());
		mRecipientStatus = (TextView) findViewById(R.id.recipient_status);
		mSubjectImage = (ImageView) findViewById(R.id.notice_subject_image);
		if (!TextUtils.isEmpty(mNotice.getCategoryId() + "")
				&& mNotice.getCategoryId() != -1) {
			mSubjectImage.setImageResource(NSResourceManager.getResourceID(
					"iconfont_type_" + mNotice.getCategoryId() + "_64",
					"drawable"));
		} else {
			mSubjectImage.setImageResource(NSResourceManager.getResourceID(
					"iconfont_subject_default", "drawable"));
		}

		mReceiveLayout = (RelativeLayout) findViewById(R.id.receive_layout);
		mReplyLayout = (RelativeLayout) findViewById(R.id.reply_layout);
		mDeleteLayout = (RelativeLayout) findViewById(R.id.delete_layout);
		mDeleteLayout.setClickable(true);
		mAlarmLayout = (RelativeLayout) findViewById(R.id.alarm_layout);
		mSetTopLayout = (RelativeLayout) findViewById(R.id.set_top_layout);
		mAlarmCb = (CheckBox) findViewById(R.id.alarm_cb);
		mSetTopCb = (CheckBox) findViewById(R.id.set_top_cb);

		if (mSendUserFlag) {
			mReceiveLayout.setVisibility(View.VISIBLE);
		} else {
			mReceiveLayout.setVisibility(View.GONE);
		}

		if (mReplyFlag) {
			mReplyLayout.setVisibility(View.VISIBLE);
		} else {
			mReplyLayout.setVisibility(View.GONE);
		}
	}

	@Override
	public void onError(int requestID, String errorCode, String errorDesc) {
		NSLog.i(TAG, "onError");
		NSLog.e(errorCode);
		// showLoadFailView();
		switch (requestID) {
		case NSBizConstant.REQUEST_GET_NOTICE_WITH_RECIPIENT_STATUS:
			showContentView();
			// string temp
			showToast("get recipient info fail...");
			break;
		case NSBizConstant.REQUEST_GET_NOTICE_WITH_RECIPIENT_STATUS_UPDATE:
			NSLog.d("update recipient status fail");
			break;
		case NSBizConstant.REQUEST_DELETE_NOTICE:
			// string temp
			showToast("delete notice fail...");
			break;
		default:
			break;
		}

	}

	@Override
	public void onResult(int requestID, String result) {
		NSLog.i(TAG, "onResult");
		NSLog.d("result json", result);
		showContentView();

		JSONObject resultObj = new JSONObject();
		JSONObject paramsObj = new JSONObject();
		try {
			resultObj = new JSONObject(result);
			paramsObj = resultObj.getJSONObject(NSBizConstant.KEY_PARAMS);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		switch (requestID) {
		case NSBizConstant.REQUEST_GET_NOTICE_WITH_RECIPIENT_STATUS:
		case NSBizConstant.REQUEST_GET_NOTICE_WITH_RECIPIENT_STATUS_UPDATE:

			Gson gson = new Gson();
			JSONArray tempArray;
			try {
				tempArray = paramsObj
						.getJSONArray(NSBizConstant.KEY_NOTICE_BEAN);
				mNoticeListFromWeb = gson.fromJson(tempArray.toString(),
						new TypeToken<List<NoticeBizBean>>() {
						}.getType());
			} catch (JSONException e) {
				e.printStackTrace();
			}

			mNotice = mNoticeListFromWeb.get(0);
			if (mNotice.getReplyFlag() == 1) {
				mReplyFlag = true;
			} else {
				mReplyFlag = false;
			}
			initTitle();
			initView();
			initAlarm();
			initEdit();
			initSetTop();
			initMenu();
			String a = getResources().getString(R.string.news_recipient_status);
			String b = String.format(a, mNotice.getGetAll(),
					mNotice.getRecipientAll());
			mRecipientStatus.setText(b);

			if (mNeedChangeReadStatusFlag) {
				mNoticeListUpdateFlag = true;
			}

			break;
		case NSBizConstant.REQUEST_DELETE_NOTICE:

			mNoticeListUpdateFlag = true;
			doBack();
			finish();
			break;
		default:
			break;
		}

	}

	private void initAlarm() {
		AlarmBizBean alarm = mAlarmService.find(mNoticeId);
		if (null != alarm) {
			mNoticeAlarmFlag = true;
			changeAlarmUI();
		}
	}

	private void initSetTop() {
		int topNoticeID = NSSharePreferenceKeeper.getIntValue(this,
				NSBizConstant.SPK_TOP_NOTICE_ID, -1);

		if (-1 != topNoticeID && topNoticeID == mNoticeId) {
			mSetTopLayout.setBackgroundColor(getResources().getColor(
					R.color.lightblue));
			mSetTopCb.setChecked(true);
			NSSharePreferenceKeeper.keepIntValue(this,
					NSBizConstant.SPK_TOP_NOTICE_ID, mNotice.getId());
		}
	}

	private void initEdit() {
		Date endDate = new Date();
		endDate.setTime(Long.parseLong(mNotice.getSendDate()));
		Date nowDate = Calendar.getInstance().getTime();
		// > 24h
		boolean tempBl = NSUtils.isFutureDayByDay(nowDate, endDate);
		if (mSendUserFlag && tempBl) {
			// mTitleRightBtn.setClickable(true);
			// mTitleRightBtn.setVisibility(View.VISIBLE);
			HashMap<String, String> editMap = new HashMap<String, String>();
			editMap.put(MORE_TYPE_EDIT, getString(R.string.comm_btn_edit));
			mMoreArray.add(editMap);
		}
	}

	@Override
	protected void onReloadContent() {
		NSLog.i(TAG, "onReloadContent");
		super.onReloadContent();
	}

	public void doLeftClick(View v) {
		NSLog.i(TAG, "doLeftClick");

		doBack();
		finish();
	}

	public void doRightClick(View v) {
		NSLog.i(TAG, "doRightClick");

		String[] showMoreItem = new String[mMoreArray.size()];
		for (int i = 0; i < mMoreArray.size(); i++) {
			showMoreItem[i] = mMoreArray.get(i).values().toString();
		}

		new AlertDialog.Builder(this)
				.setTitle(getString(R.string.comm_title_more))
				.setItems(showMoreItem, new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

						if (mMoreArray.get(which).containsKey(MORE_TYPE_DELETE)) {
							doDeleteNotice();
						} else if (mMoreArray.get(which).containsKey(
								MORE_TYPE_GET_SENDER)) {
							doGetSender();
						} else if (mMoreArray.get(which).containsKey(
								MORE_TYPE_EDIT)) {
							doEdit();
						}

						dialog.dismiss();
					}
				}).setNegativeButton(getString(R.string.comm_btn_cancel), null)
				.show();

	}

	public void doEdit() {
		Intent intent = new Intent(this, NoticeSaveActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable(NSBizConstant.KEY_NOTICE_BEAN, mNotice);
		intent.putExtras(bundle);
		startActivityForResult(intent, FLAG_ACTIVITY_REQUEST_EDIT);
	}

	public void doBack() {
		Intent backIntent = getIntent().putExtra(
				NSBizConstant.KEY_COMM_NEED_UPDATE_LIST_FLAG,
				mNoticeListUpdateFlag);
		setResult(RESULT_OK, backIntent);
	}

	public void showRecipientStatusDetail(View v) {
		NSLog.i(TAG, "showRecipientStatusDetail");
		Intent intent = new Intent(this, RecipientReadListActivity.class);
		intent.putExtra(NSBizConstant.KEY_NOTICES_CREATOR_ID,
				mNotice.getUserId());
		intent.putExtra(NSBizConstant.KEY_NOTICES_ID, mNotice.getId());
		startActivity(intent);
	}

	// public void doDeleteNotice(View v) {
	// NSLog.i(TAG, "doDeleteNotice");
	// delDialog();
	// }

	public void doDeleteNotice() {
		NSLog.i(TAG, "doDeleteNotice");
		delDialog();
	}

	protected void delDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(getString(R.string.delete_notice_msg))
				.setCancelable(false)
				.setPositiveButton(R.string.delete_notice_yes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								NSLog.d(TAG, "yes");
								// do delete notice
								delNotice();
							}
						})
				.setNegativeButton(R.string.delete_notice_no,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								NSLog.d(TAG, "no");
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

	private void delNotice() {
		NSLog.i(TAG, "delNotice");
		showLoadingView();

		String msg;
		try {
			msg = NSMsgFactory.requestDeleteNoticeMsg(mNotice.getId(), mUserId);
			postMessage(NSBizConstant.REQUEST_DELETE_NOTICE,
					NSAppConfig.SERVER_URL + NSMsgFactory.CMD_DELETE_NOTICE,
					NSHttpManager.POST, msg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void doSetTop(View v) {
		if (mSetTopCb.isChecked()) {
			mSetTopLayout.setBackgroundColor(getResources().getColor(
					R.color.lightblue));
			NSSharePreferenceKeeper.keepIntValue(this,
					NSBizConstant.SPK_TOP_NOTICE_ID, mNotice.getId());
			mNoticeListUpdateFlag = true;
			showToast(getString(R.string.top_toast_success));
		} else {
			mSetTopLayout.setBackgroundColor(getResources().getColor(
					R.color.lightgray));
			NSSharePreferenceKeeper.keepIntValue(this,
					NSBizConstant.SPK_TOP_NOTICE_ID, -1);
			mNoticeListUpdateFlag = true;
			showToast(getString(R.string.top_toast_fail));
		}
	}

	public void doSetAlarm(View v) {

		if (mAlarmCb.isChecked()) {
			showTimePicker();
		} else {
			new AlertDialog.Builder(this)
					.setTitle(getString(R.string.alarm_cancel_title))
					.setMessage(getString(R.string.alarm_cancel_content))
					.setPositiveButton(getString(R.string.comm_btn_ok),
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									doDeleteAlarm();
									mNoticeAlarmFlag = false;
									changeAlarmUI();
								}
							})
					.setNegativeButton(R.string.comm_btn_cancel,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {

									mNoticeAlarmFlag = true;
									changeAlarmUI();
								}
							}).setCancelable(false).show();

		}

	}

	private void doDeleteAlarm() {

		Intent intent = new Intent(this, NSAlarmReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
				mNotice.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmManager.cancel(pendingIntent);
		mAlarmService.delete(mNotice.getId());
		showToast(getString(R.string.notice_set_alarm_cancel));
	}

	private void changeAlarmUI() {
		if (mNoticeAlarmFlag) {
			mAlarmCb.setChecked(true);
			mAlarmLayout.setBackgroundColor(getResources().getColor(
					R.color.lightblue));
		} else {
			mAlarmCb.setChecked(false);
			mAlarmLayout.setBackgroundColor(getResources().getColor(
					R.color.lightgray));
		}
	}

	// public void doGetSender(View v) {
	//
	// }
	public void doGetSender() {
		   
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
				+ "13889595945"));
		startActivity(intent);
	}

	private void showTimePicker() {

		LayoutInflater inflater = LayoutInflater.from(this);
		final View timepickerview = inflater.inflate(R.layout.view_timepicker,
				null);
		ScreenInfo screenInfo = new ScreenInfo(this);
		wheelMain = new WheelMain(timepickerview, true);
		wheelMain.screenheight = screenInfo.getHeight();
		final Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int min = calendar.get(Calendar.MINUTE);
		wheelMain.initDateTimePicker(year, month, day, hour, min);
		new AlertDialog.Builder(this)
				.setTitle(getString(R.string.alarm_time_picker_title))
				.setView(timepickerview)
				.setPositiveButton(getString(R.string.comm_btn_ok),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								mNoticeAlarmFlag = setAlarm(
										wheelMain.getSetDay(),
										wheelMain.getSetTime());
								if (mNoticeAlarmFlag) {
									doSaveLocal(wheelMain.getTime());
									mNoticeListUpdateFlag = true;
									showToast(getString(R.string.notice_set_alarm_success));
								}
								changeAlarmUI();
							}
						})
				.setNegativeButton(getString(R.string.comm_btn_cancel),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								mNoticeAlarmFlag = false;
								changeAlarmUI();
							}
						}).setCancelable(false).show();
	}

	private void doSaveLocal(String showTime) {
		// insert db
		AlarmBizBean alarm = new AlarmBizBean();
		alarm.setNoticeId(mNotice.getId());
		alarm.setShowTime(showTime);
		mAlarmService.save(alarm);

	}

	private boolean setAlarm(String dateValue, String startTime) {

		long alarmTime = getNextAlarmTime(DATE_MODE_FIX, dateValue, startTime);
		if (alarmTime == 0) {
			showToast(getString(R.string.notice_toast_alarm_time_check_fail));
			return false;
		} else {
			Intent intent = new Intent(this, NSAlarmReceiver.class);
			intent.putExtra(NSBizConstant.KEY_NOTICES_ID, mNotice.getId());
			intent.putExtra(NSBizConstant.KEY_NOTICES_CREATOR_ID,
					mNotice.getUserId());
			PendingIntent sender = PendingIntent.getBroadcast(this,
					mNotice.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
			AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
			am.set(AlarmManager.RTC_WAKEUP, alarmTime, sender);
			return true;
		}

	}

	public long getNextAlarmTime(int dateMode, String dateValue,
			String startTime) {
		final SimpleDateFormat fmt = new SimpleDateFormat();
		final Calendar c = Calendar.getInstance();
		final long now = System.currentTimeMillis();

		try {
			if (DATE_MODE_FIX == dateMode) {
				fmt.applyPattern("yyyy-MM-dd");
				Date d = fmt.parse(dateValue);
				c.setTimeInMillis(d.getTime());
			}

			fmt.applyPattern("HH:mm");
			Date d = fmt.parse(startTime);
			c.set(Calendar.HOUR_OF_DAY, d.getHours());
			c.set(Calendar.MINUTE, d.getMinutes());
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		long nextTime = 0;
		if (DATE_MODE_FIX == dateMode) {
			nextTime = c.getTimeInMillis();
			if (now >= nextTime)
				nextTime = 0;
		}

		NSLog.d(TAG, "getNextAlarmTime->nextTime:" + nextTime);
		return nextTime;
	}

	public void showReplyList (View v) {
		Intent intent = new Intent(this, ReplyListActivity.class);
		intent.putExtra(NSBizConstant.KEY_NOTICES_ID, mNoticeId);
		startActivity(intent);
	}
	
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			NSLog.d("back key down");
			doBack();
			finish();
		}
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case FLAG_ACTIVITY_REQUEST_EDIT:

			if (resultCode == RESULT_OK) {
				mNoticeListUpdateFlag = true;
				doBack();
				finish();
			} else {
				return;
			}
			break;
		default:
			break;
		}
	}
}
