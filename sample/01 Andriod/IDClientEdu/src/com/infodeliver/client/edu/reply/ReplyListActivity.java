/**
 * 
 */
package com.infodeliver.client.edu.reply;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.infodeliver.client.base.NSActivityBase;
import com.infodeliver.client.edu.R;
import com.infodeliver.client.edu.group.GroupAddActivity;
import com.infodeliver.client.ui.GroupListAdapter;
import com.infodeliver.client.ui.ReplyListAdapter;
import com.infodeliver.client.utils.NSBizConstant;
import com.infodeliver.client.utils.NSMsgFactory;
import com.infodeliver.db.domain.GroupBizBean;
import com.infodeliver.db.domain.ReplyBizBean;
import com.infodeliver.io.NSHttpManager;
import com.infodeliver.utils.NSAppConfig;
import com.infodeliver.utils.NSLog;
import com.infodeliver.utils.NSSharePreferenceKeeper;

/**
 * Reply list
 * 
 * @author yujian
 * @version 1.0
 * @createDate 2014-08-21
 * @lastUpdate 2014-08-21
 */
public class ReplyListActivity extends NSActivityBase {
	private static final String TAG = "ReplyListActivity";

	private static final int FLAG_ACTIVITY_REQUEST_REPLY_DETAIL = 30;

	private TextView mTitleLeftBtn;
	private TextView mTitleRightBtn;
//	private TextView mTitleName;

	private ListView mReplyListView;
	private ReplyListAdapter mReplyListAdapter;
	private ArrayList<ReplyBizBean> mReplyList;
	private ArrayList<ReplyBizBean> mReplyListFromWeb;

	private String fromFlag = "";
	private String mUserId = "";
	private int mGroupId = -1;
	private int mNoticeId = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reply_list);

		mReplyList = new ArrayList<ReplyBizBean>();
		mReplyListAdapter = new ReplyListAdapter(this, mReplyList);
		mUserId = NSSharePreferenceKeeper.getStringValue(this,
				NSBizConstant.SPK_USER_ID, false);

		mNoticeId = getIntent().getIntExtra(NSBizConstant.KEY_NOTICES_ID, -1);
		fromFlag = getIntent().getStringExtra(NSBizConstant.FROM_FLAG);
		initTitle();
		initView();
		initData();
	}

	private void initView() {
		mReplyListView = (ListView) findViewById(R.id.reply_list_view);
		mReplyListView.setAdapter(mReplyListAdapter);
	}

	private void initTitle() {
		mTitleLeftBtn = (TextView) findViewById(R.id.title_left_btn);
		mTitleLeftBtn.setClickable(true);
		mTitleRightBtn = (TextView) findViewById(R.id.title_right_btn);
		mTitleRightBtn.setClickable(true);
	}

	private void initData() {

		requestReplyList();
	}

	private void requestReplyList() {
		NSLog.i(TAG, "requestReplyList");
		showLoadingView();
		String msg;
		try {
			msg = NSMsgFactory.requestReplyListMsg(mNoticeId);
			postMessage(NSBizConstant.REQUEST_GET_REPLY_LIST,
					NSAppConfig.SERVER_URL + NSMsgFactory.CMD_REPLY_LIST,
					NSHttpManager.POST, msg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
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
		showContentView();
		mReplyList.clear();
		switch (requestID) {
		case NSBizConstant.REQUEST_GET_REPLY_LIST:
			try {
				JSONObject resultObj = new JSONObject(result);
				JSONObject paramsObj = resultObj
						.getJSONObject(NSBizConstant.KEY_PARAMS);
				JSONArray replyArray = paramsObj
						.getJSONArray(NSBizConstant.KEY_REPLY_LIST);
				Gson gson = new Gson();
				mReplyListFromWeb = gson.fromJson(replyArray.toString(),
						new TypeToken<List<ReplyBizBean>>() {
						}.getType());
				mReplyList.addAll(mReplyListFromWeb);
				mReplyListAdapter.notifyDataSetChanged();
			} catch (JSONException e) {
				e.printStackTrace();
			}

			break;

		default:
			break;
		}
	}

	@Override
	protected void onReloadContent() {
		NSLog.i(TAG, "onReloadContent");
		super.onReloadContent();
	}

	public void doLeftClick(View v) {
		finish();
	}

	public void doRightClick(View v) {
		
	}

	public void doEditReply (View v) {
		Intent intent = new Intent(ReplyListActivity.this,
				ReplyActivity.class);
		intent.putExtra(NSBizConstant.KEY_NOTICES_ID, mNoticeId);
		startActivityForResult(intent, FLAG_ACTIVITY_REQUEST_REPLY_DETAIL);
	}
	
	@Override
	protected void onResume() {
		NSLog.i(TAG, "onResume");
		super.onResume();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		NSLog.d("requestCode:" + requestCode);
		NSLog.d("resultCode:" + resultCode);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case FLAG_ACTIVITY_REQUEST_REPLY_DETAIL:
				// refresh
				requestReplyList();
				break;

			default:
				break;
			}
		} else {
			setResult(RESULT_CANCELED);
		}

	}

}
