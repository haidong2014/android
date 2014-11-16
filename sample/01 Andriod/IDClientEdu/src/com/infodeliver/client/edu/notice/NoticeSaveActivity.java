package com.infodeliver.client.edu.notice;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.infodeliver.client.base.NSActivityBase;
import com.infodeliver.client.edu.R;
import com.infodeliver.client.edu.list.GroupListActivity;
import com.infodeliver.client.edu.list.NoticeSubjectImagerListActivity;
import com.infodeliver.client.ui.ScreenInfo;
import com.infodeliver.client.ui.WheelMain;
import com.infodeliver.client.utils.NSBizConstant;
import com.infodeliver.client.utils.NSMsgFactory;
import com.infodeliver.db.biz.NoticeService;
import com.infodeliver.db.domain.NoticeBizBean;
import com.infodeliver.io.NSHttpManager;
import com.infodeliver.utils.JudgeDate;
import com.infodeliver.utils.NSAppConfig;
import com.infodeliver.utils.NSLog;
import com.infodeliver.utils.NSResourceManager;
import com.infodeliver.utils.NSSharePreferenceKeeper;

/**
 * Notice save
 * 
 * @author yujian
 * @version 1.0
 * @createDate 2014-07-22
 * @lastUpdate 2014-07-22
 */
public class NoticeSaveActivity extends NSActivityBase {
	private static final String TAG = "NoticeSaveActivity";

	private static final int FLAG_ACTIVITY_REQUEST_GROUP_LIST = 30;
	private static final int FLAG_ACTIVITY_REQUEST_NOTICE_SUBJECT_IMAGE_LIST = 31;

	private TextView mTitleLeftBtn;
	private TextView mTitleRightBtn;
	private TextView mTitleName;

	private EditText mSubjectText;
	private EditText mNoticeText;
	private TextView mRecipientText;
	private ImageView mSubjectImage;
	private TextView mNoticeSendTime;

	private CheckBox mAutoCheckBox;
	private CheckBox mAlarmCheckBox;
	private CheckBox mReplyCheckBox;
	private CheckBox mRecipientCheckBox;
	private CheckBox mRecipientSelfCheckBox;

	private RelativeLayout mReplyLayout;
	private RelativeLayout mAlarmLayout;
	private RelativeLayout mSendLayout;
	private RelativeLayout mTimingSendLayout;
	private RelativeLayout mRecipientLayout;
	private RelativeLayout mRecipientSelfLayout;

	private NoticeBizBean mNotice;

	// notice list need update
	private boolean mNoticeListUpdateFlag = false;

	// save or edit flag
	private boolean mNoticeSaveOrEditFlag = true;

	private WheelMain wheelMain;
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notice_add);

		Bundle bundle = getIntent().getExtras();
		if (null != bundle) {
			// eidt
			mNoticeSaveOrEditFlag = false;
			mNotice = (NoticeBizBean) bundle
					.getSerializable(NSBizConstant.KEY_NOTICE_BEAN);
		} else {
			// save
			mNotice = new NoticeBizBean();
			mNotice.setCategoryId(-1);
		}

		initTitle();
		initView();
		if (!mNoticeSaveOrEditFlag) {
			initData();
		}
	}

	private void initTitle() {
		mTitleLeftBtn = (TextView) findViewById(R.id.title_left_btn);
		mTitleRightBtn = (TextView) findViewById(R.id.title_right_btn);
		mTitleRightBtn.setText(R.string.news_add_btn_send);
		mTitleName = (TextView) findViewById(R.id.title_name);
		mTitleLeftBtn.setClickable(true);
		mTitleRightBtn.setClickable(true);
		if (mNoticeSaveOrEditFlag) {
			mTitleName.setText(getString(R.string.news_add_title_name));
			mTitleRightBtn.setText(getString(R.string.news_add_btn_send));
		} else {
			mTitleName.setText(getString(R.string.news_edit_title_name));
			mTitleRightBtn.setText(getString(R.string.news_edit_btn_save));
		}
	}

	private void initView() {

		mReplyLayout = (RelativeLayout) findViewById(R.id.reply_layout);
		mAlarmLayout = (RelativeLayout) findViewById(R.id.remind_layout);
		mSendLayout = (RelativeLayout) findViewById(R.id.auto_layout);
		mTimingSendLayout = (RelativeLayout) findViewById(R.id.time_layout);
		mRecipientLayout = (RelativeLayout) findViewById(R.id.recipient_layout);
		mRecipientSelfLayout = (RelativeLayout) findViewById(R.id.recipient_self_layout);
		mNoticeSendTime = (TextView) findViewById(R.id.notice_send_time);
		mNoticeSendTime.setClickable(true);

		mRecipientText = (TextView) findViewById(R.id.recipient_text);
		String a = getResources().getString(R.string.news_add_send_to);
		String b = String.format(a, 0);
		mRecipientText.setText(b);

		mAutoCheckBox = (CheckBox) findViewById(R.id.auto_cb);
		mAlarmCheckBox = (CheckBox) findViewById(R.id.remind_cb);
		mReplyCheckBox = (CheckBox) findViewById(R.id.reply_cb);
		mRecipientCheckBox = (CheckBox) findViewById(R.id.recipient_cb);
		mRecipientSelfCheckBox = (CheckBox) findViewById(R.id.recipient_self_cb);

		mSubjectImage = (ImageView) findViewById(R.id.notice_subject_image);
		mSubjectImage.setClickable(true);
		mSubjectText = (EditText) findViewById(R.id.notice_subject);
		mNoticeText = (EditText) findViewById(R.id.notice_text);
	}

	private void initData() {
		int subjectID = mNotice.getCategoryId();
		if (!TextUtils.isEmpty(subjectID + "") && subjectID != -1) {
			mSubjectImage.setImageResource(NSResourceManager.getResourceID(
					"iconfont_type_" + subjectID + "_64", "drawable"));
		} else {
			mNotice.setCategoryId(-1);
			mSubjectImage.setImageResource(NSResourceManager.getResourceID(
					"iconfont_subject_default", "drawable"));
		}

		mSubjectText.setText(mNotice.getSubjectText());
		mNoticeText.setText(mNotice.getText());

		if (1 == mNotice.getReplyFlag()) {
			mReplyCheckBox.setChecked(true);
			mReplyLayout.setBackgroundColor(getResources().getColor(
					R.color.lightblue));
		} else {
			mReplyCheckBox.setChecked(false);
			mReplyLayout.setBackgroundColor(getResources().getColor(
					R.color.lightgray));
		}
	}

	@Override
	public void onError(int requestID, String errorCode, String errorDesc) {
		NSLog.i(TAG, "onError");
		NSLog.e(errorCode);
		showLoadFailView();
		switch (requestID) {
		case NSBizConstant.REQUEST_SAVE_NOTICE:
			showToast("Save fail.");
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
		switch (requestID) {
		case NSBizConstant.REQUEST_SAVE_NOTICE:
		case NSBizConstant.REQUEST_EDIT_NOTICE:

			// do update local id by web id
			mNoticeListUpdateFlag = true;
			Intent backIntent = getIntent().putExtra(
					NSBizConstant.KEY_COMM_NEED_UPDATE_LIST_FLAG,
					mNoticeListUpdateFlag);
			setResult(RESULT_OK, backIntent);
			showToast("Save OK");
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	protected void onReloadContent() {
		NSLog.i(TAG, "onReloadContent");
		super.onReloadContent();
		save();
	}

	public void doLeftClick(View v) {
		setResult(RESULT_CANCELED);
		finish();
	}

	public void doRightClick(View v) {
		NSLog.i(TAG, "doRightClick");

		if (mNoticeSaveOrEditFlag) {
			save();
		} else {
			edit();
		}
		
	}

	public void edit() {
		// Check
		if (checkForm()) {
			// WEB
			updateNoticeToWeb();
		}
	}
	
	public void save() {
		// Check
		if (checkForm()) {
			// WEB
			saveNoticeToWeb();
		}
	}

	private boolean checkForm() {
		NSLog.i(TAG, "checkForm");

		// check category
		if (mNotice.getCategoryId() == -1) {
			showToast("Need:" + mSubjectImage.getContentDescription());
			return false;
		}

		// subject check
		String subject = mSubjectText.getText().toString();
		if (TextUtils.isEmpty(subject)) {
			showToast("Need:" + mSubjectText.getHint());
			return false;
		} else {
			mNotice.setSubjectText(subject);
		}

		// text check
		String noticeText = mNoticeText.getText().toString();
		if (TextUtils.isEmpty(noticeText)) {
			showToast("Need:" + mNoticeText.getHint());
			return false;
		} else {
			mNotice.setText(noticeText);
		}

		// recipient check
		if (mRecipientCheckBox.isChecked()) {
			mNotice.setRecipinetType(0);
			String recipientArray = mNotice.getRecipientArray();
			NSLog.d(TAG, "recipientArray:" + recipientArray);
			if (TextUtils.isEmpty(recipientArray)) {
				showToast("Need:" + mRecipientText.getHint());
				return false;
			}
		} else {
			mNotice.setRecipinetType(1);
			mNotice.setRecipientArray("");
		}

		// reply
		if (mReplyCheckBox.isChecked()) {
			mNotice.setReplyFlag(1);
		} else {
			mNotice.setReplyFlag(0);
		}

		// send time
		boolean sendFlag = mAutoCheckBox.isChecked();
		// Calendar calendar = Calendar.getInstance();
		String nowTime = (new SimpleDateFormat("yyyy-MM-dd HH:mm"))
				.format(new Date());

		NSLog.i(TAG, "nowTime:" + nowTime);
		if (sendFlag) {
			mNotice.setSendDate(nowTime);
		} else {
			String setTime = mNoticeSendTime.getText().toString();
			NSLog.i(TAG, "setTime:" + setTime);
			long diff = 0;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			try {
				Date d1 = df.parse(setTime);
				Date d2 = df.parse(nowTime);
				diff = d1.getTime() - d2.getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}

			if (diff < 0) {
				showToast(getString(R.string.news_toast_data_time_check_fail));
				return false;
			} else {
				mNotice.setSendDate(setTime + "");
			}
		}

		// user id from sp
		String userId = NSSharePreferenceKeeper.getStringValue(this,
				NSBizConstant.SPK_USER_ID, false);
		NSLog.d(TAG, "userId:" + userId);
		mNotice.setUserId(userId);

		return true;
	}

	private void saveNoticeToDB() {
		NSLog.i(TAG, "saveNoticeToDB");
		NoticeService noticeService = new NoticeService(this);
		noticeService.save(mNotice);
	}

	private void updateNoticeToWeb() {
		NSLog.i(TAG, "updateNoticeToWeb");
		showLoadingView();

		String msg;
		try {
			msg = NSMsgFactory.requestEditNoticeMsg(mNotice);
			postMessage(NSBizConstant.REQUEST_EDIT_NOTICE,
					NSAppConfig.SERVER_URL + NSMsgFactory.CMD_NOTICE_EDIT,
					NSHttpManager.POST, msg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void saveNoticeToWeb() {
		NSLog.i(TAG, "saveNoticeToWeb");
		showLoadingView();

		String msg;
		try {
			msg = NSMsgFactory.requestSaveNoticeMsg(mNotice);
			postMessage(NSBizConstant.REQUEST_SAVE_NOTICE,
					NSAppConfig.SERVER_URL + NSMsgFactory.CMD_NOTICE_SAVE,
					NSHttpManager.POST, msg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void doGroupList(View v) {

		Intent intent = new Intent(this, GroupListActivity.class);
		intent.putExtra(NSBizConstant.FROM_FLAG, NSBizConstant.FROM_FLAG_NOTICE);
		startActivityForResult(intent, FLAG_ACTIVITY_REQUEST_GROUP_LIST);
	}

	public void doChangeTime(View v) {
		NSLog.i(TAG, "show timer");
		showTimePicker();
	}

	public void doReply(View v) {
		CheckBox checkAutoSend = (CheckBox) v;
		boolean checkFlg = checkAutoSend.isChecked();

		if (checkFlg) {
			mReplyLayout.setBackgroundColor(getResources().getColor(
					R.color.lightblue));
		} else {
			mReplyLayout.setBackgroundColor(getResources().getColor(
					R.color.lightgray));
		}

	}

	public void doCheckSend(View v) {
		boolean checkFlg = true;

		if (checkFlg) {
			mAlarmLayout.setBackgroundColor(getResources().getColor(
					R.color.lightgray));
			mAlarmCheckBox.setChecked(false);
			mSendLayout.setBackgroundColor(getResources().getColor(
					R.color.lightblue));
			mAutoCheckBox.setChecked(true);
			mTimingSendLayout.setVisibility(View.GONE);

		}
	}

	public void doCheckTimingSend(View v) {
		boolean checkFlg = true;

		if (checkFlg) {
			mSendLayout.setBackgroundColor(getResources().getColor(
					R.color.lightgray));
			mAutoCheckBox.setChecked(false);
			mAlarmLayout.setBackgroundColor(getResources().getColor(
					R.color.lightblue));
			mAlarmCheckBox.setChecked(true);
			mTimingSendLayout.setVisibility(View.VISIBLE);

			Calendar calendar = Calendar.getInstance();
			mNoticeSendTime.setText(calendar.get(Calendar.YEAR) + "-"
					+ (calendar.get(Calendar.MONTH) + 1) + "-"
					+ calendar.get(Calendar.DAY_OF_MONTH) + " "
					+ calendar.get(Calendar.HOUR_OF_DAY) + "-"
					+ calendar.get(Calendar.MINUTE) + "");
			showTimePicker();

		}
	}

	private void showTimePicker() {
		LayoutInflater inflater = LayoutInflater.from(this);
		final View timepickerview = inflater.inflate(R.layout.view_timepicker,
				null);
		ScreenInfo screenInfo = new ScreenInfo(this);
		wheelMain = new WheelMain(timepickerview, true);
		wheelMain.screenheight = screenInfo.getHeight();
		// 读取现有时间 fail
		// String time = mNoticeSendTime.getText().toString();
		final Calendar calendar = Calendar.getInstance();
		// if (JudgeDate.isDate(time, "yyyy-MM-dd-HH-mm")) {
		// try {
		// calendar.setTime(dateFormat.parse(time));
		// } catch (ParseException e) {
		// e.printStackTrace();
		// }
		// }
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int min = calendar.get(Calendar.MINUTE);
		wheelMain.initDateTimePicker(year, month, day, hour, min);
		new AlertDialog.Builder(this)
				.setTitle(getString(R.string.comm_view_time_picker))
				.setView(timepickerview)
				.setPositiveButton(getString(R.string.comm_btn_ok),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								mNoticeSendTime.setText(wheelMain.getTime());

							}
						})
				.setNegativeButton(getString(R.string.comm_btn_cancel),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								mSendLayout.setBackgroundColor(getResources()
										.getColor(R.color.lightblue));
								mAutoCheckBox.setChecked(true);
								mAlarmLayout.setBackgroundColor(getResources()
										.getColor(R.color.lightgray));
								mAlarmCheckBox.setChecked(false);
								mTimingSendLayout.setVisibility(View.GONE);
							}
						}).setCancelable(false).show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		NSLog.d("requestCode:" + requestCode);
		NSLog.d("resultCode:" + resultCode);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case FLAG_ACTIVITY_REQUEST_GROUP_LIST:

				Intent backIntent = data;
				int checkedCount = backIntent.getIntExtra(
						NSBizConstant.KEY_CHECKED_COUNT, 0);
				String checkArray = backIntent
						.getStringExtra(NSBizConstant.KEY_CHECKED_ARRAY_STR);
				int groupId = backIntent
						.getIntExtra(NSBizConstant.KEY_GROUP_ID , 0);
				mNotice.setGroupId(groupId);
				NSLog.d("checkedCount:" + checkedCount);
				NSLog.d("checkArray:" + checkArray);
				mNotice.setRecipientArray(checkArray);

				String a = getResources().getString(R.string.news_add_send_to);
				String b = String.format(a, checkedCount);
				mRecipientText.setText(b);
				break;
			case FLAG_ACTIVITY_REQUEST_NOTICE_SUBJECT_IMAGE_LIST:
				int subjectID = data.getIntExtra(
						NSBizConstant.KEY_NOTICE_SUBJECT, -1);
				// int subjectImageID =
				// data.getIntExtra(NSBizConstant.KEY_NOTICE_SUBJECT_IMAGE_ID,
				// 0);
				// if (subjectImageID != 0) {
				// mSubjectImage.setImageResource(subjectImageID);
				// mNotice.setCategoryId(subjectID);
				// }
				if (!TextUtils.isEmpty(subjectID + "") && subjectID != -1) {
					mNotice.setCategoryId(subjectID);
					mSubjectImage.setImageResource(NSResourceManager
							.getResourceID(
									"iconfont_type_" + subjectID + "_64",
									"drawable"));
				} else {
					mNotice.setCategoryId(-1);
					mSubjectImage.setImageResource(NSResourceManager
							.getResourceID("iconfont_subject_default",
									"drawable"));
				}
				break;
			default:
				break;
			}
		} else {
			setResult(RESULT_CANCELED);
		}
	}

	public void doSelectSubject(View v) {

		Intent intent = new Intent(this, NoticeSubjectImagerListActivity.class);
		startActivityForResult(intent,
				FLAG_ACTIVITY_REQUEST_NOTICE_SUBJECT_IMAGE_LIST);
	}

	public void doRecipientCheckBox(View v) {
		mRecipientCheckBox.setChecked(true);
		mRecipientLayout.setBackgroundColor(getResources().getColor(
				R.color.lightblue));
		mRecipientLayout.setClickable(true);
		mRecipientSelfCheckBox.setChecked(false);
		mRecipientSelfLayout.setBackgroundColor(getResources().getColor(
				R.color.lightgray));
	}

	public void doRecipientSelfCheckBox(View v) {

		mRecipientSelfCheckBox.setChecked(true);
		mRecipientSelfLayout.setBackgroundColor(getResources().getColor(
				R.color.lightblue));
		mRecipientCheckBox.setChecked(false);
		mRecipientLayout.setBackgroundColor(getResources().getColor(
				R.color.lightgray));
		mRecipientLayout.setClickable(false);
	}
}
