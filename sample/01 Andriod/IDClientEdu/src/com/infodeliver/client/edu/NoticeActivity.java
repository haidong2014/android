package com.infodeliver.client.edu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.infodeliver.client.base.NSActivityBase;
import com.infodeliver.client.edu.notice.NoticeDetailActivity;
import com.infodeliver.client.edu.notice.NoticeSaveActivity;
import com.infodeliver.client.ui.NoticeListAdapter;
import com.infodeliver.client.utils.NSBizConstant;
import com.infodeliver.client.utils.NSMsgFactory;
import com.infodeliver.db.biz.AlarmService;
import com.infodeliver.db.domain.AlarmBizBean;
import com.infodeliver.db.domain.DiaryBizBean;
import com.infodeliver.db.domain.NoticeBizBean;
import com.infodeliver.io.NSHttpManager;
import com.infodeliver.ui.NSOnClickListener;
import com.infodeliver.ui.NSOnPullDownListener;
import com.infodeliver.ui.NSPullDownView;
import com.infodeliver.utils.NSAppConfig;
import com.infodeliver.utils.NSLog;
import com.infodeliver.utils.NSSharePreferenceKeeper;

/**
 * Notice
 * 
 * @author yujian
 * @version 1.0
 * @createDate 2014-07-22
 * @lastUpdate 2014-07-22
 */
public class NoticeActivity extends NSActivityBase implements
		NSOnPullDownListener, NSOnClickListener {
	private static final String TAG = "NoticeActivity";

	private static final int FLAG_ACTIVITY_REQUEST_DETAIL = 30;
	private static final int FLAG_ACTIVITY_REQUEST_SAVE = 31;
	private static final int WHAT_DID_GET_NOTICE_SERVER = 20;

	private TextView mTitleLeftBtn;
	private TextView mTitleRightBtn;

	private ListView mNoticeListView;
	private ListView mNoticeTopListView;
	private NoticeListAdapter mNoticeListAdapter;
	private NoticeListAdapter mNoticeTopListAdapter;
	private ArrayList<NoticeBizBean> mNoticeList;
	private ArrayList<NoticeBizBean> mNoticeTopList;
	private ArrayList<NoticeBizBean> mNoticeListFromWeb;

	private RelativeLayout mNoticeTop;

	private HashMap<Integer, AlarmBizBean> mAlarmMap;
	private NSPullDownView mNoticePullDownView;

	private int mLastSelectTopId = -1;
	private int mLastSelectEndId = -1;
	private String mLastSelectEndDate = "";
	private String mLastSelectTopDate = "";

	private String mUserId = "";

	private int mTopNoticeID = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		NSLog.i(TAG, "NoticeActivity>>>onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notice_tab);

		showLoadingView();
		// user id from sp
		mUserId = NSSharePreferenceKeeper.getStringValue(this,
				NSBizConstant.SPK_USER_ID, false);
		mNoticeList = new ArrayList<NoticeBizBean>();
		mNoticeListAdapter = new NoticeListAdapter(this, mNoticeList);
		mNoticeTopList = new ArrayList<NoticeBizBean>();
		mNoticeTopListAdapter = new NoticeListAdapter(this, mNoticeTopList);
		mTopNoticeID = NSSharePreferenceKeeper.getIntValue(this,
				NSBizConstant.SPK_TOP_NOTICE_ID, -1);
		initTitle();
		initView();

		cleanFlag();
		requestNoticeFromWeb();
	}

	private void initTitle() {

		mTitleLeftBtn = (TextView) findViewById(R.id.title_left_btn);
		mTitleRightBtn = (TextView) findViewById(R.id.title_right_btn);
		mTitleLeftBtn.setClickable(true);
		mTitleRightBtn.setClickable(true);
	}

	private void initView() {

		// init list
		mNoticePullDownView = (NSPullDownView) findViewById(R.id.news_pull_down_view);
		mNoticePullDownView.initHeaderViewAndFooterViewAndListView(
				"comm_pull_head", "comm_pull_footer");
		mNoticePullDownView.setOnPullDownListener(this);
		mNoticeListView = mNoticePullDownView.getListView();
		mNoticeListView.setAdapter(mNoticeListAdapter);
		// auto
		mNoticePullDownView.enableAutoFetchMore(true, 1);
		mNoticePullDownView.setHideFooter();
		mNoticePullDownView.setShowFooter();
		mNoticePullDownView.setHideHeader();
		mNoticePullDownView.setShowHeader();
		// init pull
		mNoticePullDownView.notifyDidMore();
		mNoticeListView.setOnItemClickListener(onNoticeItemClick);
		mNoticeTopListView = (ListView) findViewById(R.id.top_list_view);
		mNoticeTopListView.setAdapter(mNoticeTopListAdapter);
		mNoticeTopListView.setOnItemClickListener(onNoticeTopItemClick);
		mNoticeTop = (RelativeLayout) findViewById(R.id.notice_top);
		if (-1 == mTopNoticeID) {
			mNoticeTop.setVisibility(View.GONE);
		} else {
			mNoticeTop.setVisibility(View.VISIBLE);
		}
	}

	private void requestNoticeFromWeb() {
		NSLog.i(TAG, "requestNoticeFromWeb");
		showLoadingView();

		String msg;
		try {
			msg = NSMsgFactory.requestNoticeMsg(0, 6, mUserId);
			postMessage(NSBizConstant.REQUEST_GET_NOTICE,
					NSAppConfig.SERVER_URL + NSMsgFactory.CMD_NOTICE_GET,
					NSHttpManager.POST, msg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void requestMoreNoticeFromWeb() {
		NSLog.i(TAG, "requestNoticeFromWeb");
		String msg;
		try {
			msg = NSMsgFactory.requestMoreNoticeMsg(mLastSelectEndId, 0, 2,
					mUserId);
			postMessage(NSBizConstant.REQUEST_GET_NOTICE_MORE,
					NSAppConfig.SERVER_URL + NSMsgFactory.CMD_NOTICE_GET_MORE,
					NSHttpManager.POST, msg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void requestNewNoticeFromWeb() {
		NSLog.i(TAG, "requestNoticeFromWeb");
		String msg;
		try {
			msg = NSMsgFactory.requestNewNoticeMsg(mLastSelectTopId, 0, 2,
					mUserId);
			postMessage(NSBizConstant.REQUEST_GET_NOTICE_NEW,
					NSAppConfig.SERVER_URL + NSMsgFactory.CMD_NOTICE_GET_NEW,
					NSHttpManager.POST, msg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private OnItemClickListener onNoticeItemClick = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int position,
				long index) {

			int beanIndex = (int) index;
			NoticeBizBean bizBean = mNoticeList.get(beanIndex);

			if (NoticeListAdapter.TYPE_NOTICE == bizBean.getLayoutType()) {
				Intent intent = new Intent(NoticeActivity.this,
						NoticeDetailActivity.class);
				intent.putExtra(NSBizConstant.KEY_NOTICES_ID, bizBean.getId());
				intent.putExtra(NSBizConstant.KEY_NOTICES_CREATOR_ID,
						bizBean.getUserId());
				intent.putExtra(
						NSBizConstant.KEY_NEED_CHANGE_RECIPIENT_READ_STATUS_FLAG,
						bizBean.getReadFlag());
				startActivityForResult(intent, FLAG_ACTIVITY_REQUEST_DETAIL);
			}
		}
	};

	private OnItemClickListener onNoticeTopItemClick = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int position,
				long index) {

			int beanIndex = (int) index;
			NoticeBizBean bizBean = mNoticeTopList.get(beanIndex);

			if (NoticeListAdapter.TYPE_NOTICE == bizBean.getLayoutType()) {
				Intent intent = new Intent(NoticeActivity.this,
						NoticeDetailActivity.class);
				intent.putExtra(NSBizConstant.KEY_NOTICES_ID, bizBean.getId());
				intent.putExtra(NSBizConstant.KEY_NOTICES_CREATOR_ID,
						bizBean.getUserId());
				intent.putExtra(
						NSBizConstant.KEY_NEED_CHANGE_RECIPIENT_READ_STATUS_FLAG,
						bizBean.getReadFlag());
				startActivityForResult(intent, FLAG_ACTIVITY_REQUEST_DETAIL);
			}
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		boolean needUpdateFlag = false;
		switch (requestCode) {
		case FLAG_ACTIVITY_REQUEST_DETAIL:
			NSLog.d(TAG, "message detail back");

			if (data != null) {
				needUpdateFlag = data.getBooleanExtra(
						NSBizConstant.KEY_COMM_NEED_UPDATE_LIST_FLAG, false);
				if (needUpdateFlag) {
					// do update
					cleanFlag();
					requestNoticeFromWeb();
				}
			}

			break;
		case FLAG_ACTIVITY_REQUEST_SAVE:
			NSLog.d(TAG, "message add back");

			if (data != null) {
				needUpdateFlag = data.getBooleanExtra(
						NSBizConstant.KEY_COMM_NEED_UPDATE_LIST_FLAG, false);
				if (needUpdateFlag) {
					// do update
					cleanFlag();
					requestNoticeFromWeb();
				}
			}

			break;
		default:
			break;
		}
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				200, 200);
		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

	}

	@Override
	public void onError(int requestID, String errorCode, String errorDesc) {
		NSLog.i(TAG, "onError");
		NSLog.e(errorCode);
		showLoadFailView();
	}

	@Override
	public void onResult(int requestID, String result) {
		NSLog.i(TAG, "onResult");
		NSLog.d("result json", result);
		try {
			JSONObject resultObj = new JSONObject(result);
			JSONObject paramsObj = resultObj
					.getJSONObject(NSBizConstant.KEY_PARAMS);
			JSONArray noticeArray = paramsObj
					.getJSONArray(NSBizConstant.KEY_NOTICES);
			Gson gson = new Gson();
			mNoticeListFromWeb = gson.fromJson(noticeArray.toString(),
					new TypeToken<List<NoticeBizBean>>() {
					}.getType());

			mTopNoticeID = NSSharePreferenceKeeper.getIntValue(this,
					NSBizConstant.SPK_TOP_NOTICE_ID, -1);
			if (-1 != mTopNoticeID) {
				// edit top
				mNoticeTop.setVisibility(View.VISIBLE);
				editTopNotice();
			} else {
				mNoticeTop.setVisibility(View.GONE);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		NSLog.d("size:" + mNoticeListFromWeb.size());
		switch (requestID) {
		case NSBizConstant.REQUEST_GET_NOTICE:
			editNoticeList(true);
			Message msg = mUIHandler.obtainMessage(WHAT_DID_GET_NOTICE_SERVER);
			msg.sendToTarget();
			break;
		case NSBizConstant.REQUEST_GET_NOTICE_MORE:
			editNoticeList(false);
			mNoticePullDownView.notifyDidMore();
			Message msgMore = mUIHandler.obtainMessage(WHAT_DID_MORE);
			msgMore.sendToTarget();
			break;
		case NSBizConstant.REQUEST_GET_NOTICE_NEW:
			editNoticeListForRefresh();
			mNoticePullDownView.RefreshComplete();
			Message msgRefresh = mUIHandler.obtainMessage(WHAT_DID_REFRESH);
			msgRefresh.sendToTarget();
			break;
		default:
			break;
		}
	}

	private void editNoticeList(boolean saveTopInfoFlag) {
		// EDIT
		NoticeBizBean dateNotice = null;

		if (mNoticeListFromWeb.size() <= 0) {
			showToast(getString(R.string.news_toast_nodata));
			return;
		}

		for (int i = 0; i < mNoticeListFromWeb.size(); i++) {
			// mLastSelectEndDate
			NoticeBizBean notice = mNoticeListFromWeb.get(i);

			if (saveTopInfoFlag && i == 0) {
				mLastSelectTopId = notice.getId();
				mLastSelectTopDate = notice.getSendDay();
			}

			if (mLastSelectEndDate.equals(notice.getSendDay())) {
				notice.setLayoutType(NoticeListAdapter.TYPE_NOTICE);
				mNoticeList.add(notice);
			} else {
				// save date
				mLastSelectEndDate = notice.getSendDay();
				dateNotice = new NoticeBizBean();
				dateNotice.setLayoutType(NoticeListAdapter.TYPE_DATE);
				dateNotice.setSendDay(mLastSelectEndDate);
				mNoticeList.add(dateNotice);
				// save notice
				notice.setLayoutType(NoticeListAdapter.TYPE_NOTICE);
				mNoticeList.add(notice);
			}

			mLastSelectEndId = notice.getId();
		}
	}

	private void editNoticeListForRefresh() {
		// EDIT
		NoticeBizBean dateNotice = null;
		ArrayList<NoticeBizBean> tempNoticeList = new ArrayList<NoticeBizBean>();
		for (int i = 0; i < mNoticeListFromWeb.size(); i++) {
			// mLastSelectEndDate
			NoticeBizBean notice = mNoticeListFromWeb.get(i);

			if (i == 0) {
				mLastSelectTopId = notice.getId();
			}

			if (mLastSelectTopDate.equals(notice.getSendDay())) {
				notice.setLayoutType(NoticeListAdapter.TYPE_NOTICE);
				tempNoticeList.add(notice);
			} else {
				// save date
				mLastSelectTopDate = notice.getSendDay();
				dateNotice = new NoticeBizBean();
				dateNotice.setLayoutType(NoticeListAdapter.TYPE_DATE);
				dateNotice.setSendDay(mLastSelectEndDate);
				tempNoticeList.add(dateNotice);
				// save notice
				notice.setLayoutType(NoticeListAdapter.TYPE_NOTICE);
				tempNoticeList.add(notice);
			}
		}

		if (tempNoticeList.size() > 0) {
			// delete old top one
			mNoticeList.remove(0);
		} else {
			showToast(getString(R.string.news_toast_newest));
			return;
		}

		for (int i = tempNoticeList.size() - 1; i >= 0; i--) {
			// save
			mNoticeList.add(0, tempNoticeList.get(i));
		}
		// init
		mLastSelectTopDate = "";
		tempNoticeList.clear();
	}

	private void editTopNotice() {

		mNoticeTopList.clear();
		for (int i = 0; i < mNoticeListFromWeb.size(); i++) {
			if (mNoticeListFromWeb.get(i).getId() == mTopNoticeID) {
				mNoticeListFromWeb.get(i).setLayoutType(
						NoticeListAdapter.TYPE_NOTICE);
				mNoticeTopList.add(mNoticeListFromWeb.get(i));
				mNoticeListFromWeb.remove(i);
			}
		}
		mNoticeTopListAdapter.notifyDataSetChanged();
	}

	@Override
	protected void onReloadContent() {
		NSLog.i(TAG, "onReloadContent");
		super.onReloadContent();
		cleanFlag();
		requestNoticeFromWeb();
	}

	@Override
	protected void onResume() {
		NSLog.i(TAG, "onResume");
		super.onResume();
		boolean pushNeedJump = NSSharePreferenceKeeper.getBooleanValue(this,
				NSBizConstant.SPK_PUSH_NEED_RESUME);
		NSLog.d(TAG, "pushNeedJump:" + pushNeedJump);
		if (pushNeedJump) {
			// cleanFlag();
			// requestNoticeFromWeb();
			NSSharePreferenceKeeper.keepBooleanValue(this,
					NSBizConstant.SPK_PUSH_NEED_RESUME, false);
			int noticeId = NSSharePreferenceKeeper.getIntValue(this,
					NSBizConstant.SPK_PUSH_NOTICE_ID);
			String noticeCreatorId = NSSharePreferenceKeeper.getStringValue(
					this, NSBizConstant.SPK_PUSH_NOTICE_CREATOR_ID);
			doDetailJump(noticeId, noticeCreatorId);

			return;
		}
		boolean pushAlarm = NSSharePreferenceKeeper.getBooleanValue(this,
				NSBizConstant.SPK_PUSH_ALARM);
		if (pushAlarm) {

			// local clear
			final int noticeId = NSSharePreferenceKeeper.getIntValue(this,
					NSBizConstant.SPK_PUSH_NOTICE_ID);
			final String noticeCreatorId = NSSharePreferenceKeeper
					.getStringValue(this,
							NSBizConstant.SPK_PUSH_NOTICE_CREATOR_ID);
			NSLog.d("noticeId:" + noticeId);
			NSLog.d("noticeCreatorId" + noticeCreatorId);
			if (-1 == noticeId || TextUtils.isEmpty(noticeCreatorId)) {
				// for test
				showToast("for test show alarm fail");
				return;
			}

			NSSharePreferenceKeeper.keepBooleanValue(this,
					NSBizConstant.SPK_PUSH_ALARM, false);

			final AlarmService alarmService = new AlarmService(this);

			new AlertDialog.Builder(this)
					.setTitle(getString(R.string.alarm_title))
					.setMessage(getString(R.string.alarm_content))
					.setPositiveButton(getString(R.string.alarm_btn_ok),
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									alarmService.delete(noticeId);
									doDetailJump(noticeId, noticeCreatorId);
								}
							})
					.setNegativeButton(R.string.alarm_btn_cancel,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									NSLog.d(TAG, "no");
								}
							}).setCancelable(false).show();
		}
	}

	private void doDetailJump(int noticeId, String noticeCreatorId) {

		if (-1 != noticeId && !"".equals(noticeCreatorId)) {
			Intent intent = new Intent(NoticeActivity.this,
					NoticeDetailActivity.class);
			intent.putExtra(NSBizConstant.KEY_NOTICES_ID, noticeId);
			intent.putExtra(NSBizConstant.KEY_NOTICES_CREATOR_ID,
					noticeCreatorId);
			startActivityForResult(intent, FLAG_ACTIVITY_REQUEST_DETAIL);
		}
	}

	public void doLeftClick(View v) {
	}

	public void doRightClick(View v) {
		Intent intent = new Intent(this, NoticeSaveActivity.class);
		startActivityForResult(intent, FLAG_ACTIVITY_REQUEST_SAVE);
	}

	@SuppressLint("HandlerLeak")
	private Handler mUIHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case WHAT_DID_LOAD_DATA: {
				if (msg.obj != null) {
				}
				break;
			}
			case WHAT_DID_REFRESH: {
				showContentView();
				mNoticeListAdapter.notifyDataSetChanged();
				break;
			}
			case WHAT_DID_MORE: {
				showContentView();
				mNoticeListAdapter.notifyDataSetChanged();
				break;
			}
			case WHAT_DID_GET_NOTICE_SERVER: {
				showContentView();
				// update alarm map
				doUpdateAlarmMap();
				mNoticeTopListAdapter.setmAlarmMap(mAlarmMap);
				mNoticeListAdapter.setmAlarmMap(mAlarmMap);
				mNoticeListAdapter.notifyDataSetChanged();
				break;
			}
			}
		}
	};

	@Override
	public void onRefresh() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				requestNewNoticeFromWeb();
			}
		}).start();

	}

	@Override
	public void onMore() {
		NSLog.i(TAG, "onMore");
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				requestMoreNoticeFromWeb();
			}
		}).start();
	}

	@Override
	public void onClick(View item, int position, int which) {
		switch (which) {
		case R.id.msg_remind:
			break;
		case R.id.msg_edit:
			NSLog.i(TAG, "edit position:" + position);
			showToast("Edit:" + position);
			break;
		case R.id.msg_read:
			NSLog.i(TAG, "read position:" + position);
			showToast("Edit:" + position);
			break;
		default:
			break;
		}
	}

	private void cleanFlag() {
		mLastSelectTopId = -1;
		mLastSelectEndId = -1;
		mLastSelectEndDate = "";
		mLastSelectTopDate = "";
		mNoticeList.clear();
	}

	private void doUpdateAlarmMap() {
		NSLog.i(TAG, "doUpdateAlarmMap");
		mAlarmMap = new HashMap<Integer, AlarmBizBean>();
		AlarmService alarmService = new AlarmService(this);
		Cursor cursor = alarmService.getAllData();
		AlarmBizBean alarm = null;
		mAlarmMap.clear();
		while (cursor.moveToNext()) {
			alarm = new AlarmBizBean();
			int noticeId = cursor.getInt(0);
			alarm.setNoticeId(noticeId);
			alarm.setShowTime(cursor.getString(1));
			mAlarmMap.put(noticeId, alarm);
		}
		NSLog.d("alarm count:" + mAlarmMap.size());
	}

	@Override
	public void onPrivateEvent(int position, int which, Object eventObject) {
		NSLog.i(TAG, "onPrivateEvent");
		switch (which) {
		case R.id.msg_remind:
			String a = getResources().getString(R.string.notice_toast_alarm_time);
			String b = String.format(a, eventObject.toString());
			showToast(b);
			break;
		default:
			break;
		}
		
		
	}

}
