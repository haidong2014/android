/**
 * 
 */
package com.infodeliver.client.edu.list;

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
import com.infodeliver.client.utils.NSBizConstant;
import com.infodeliver.client.utils.NSMsgFactory;
import com.infodeliver.db.domain.GroupBizBean;
import com.infodeliver.db.domain.NoticeBizBean;
import com.infodeliver.io.NSHttpManager;
import com.infodeliver.utils.NSAppConfig;
import com.infodeliver.utils.NSLog;
import com.infodeliver.utils.NSSharePreferenceKeeper;

/**
 * Group list
 * 
 * @author yujian
 * @version 1.0
 * @createDate 2014-07-28
 * @lastUpdate 2014-07-28
 */
public class GroupListActivity extends NSActivityBase {
	private static final String TAG = "GroupListActivity";

	private static final int FLAG_ACTIVITY_REQUEST_MEMBER_LIST = 30;
	private static final int FLAG_ACTIVITY_REQUEST_ADD_GROUP = 31;

	private TextView mTitleLeftBtn;
	private TextView mTitleRightBtn;
	private TextView mTitleName;

	private ListView mGroupListView;
	private GroupListAdapter mGroupListAdapter;
	private ArrayList<GroupBizBean> mGroupList;
	private ArrayList<GroupBizBean> mGroupListFromWeb;

	private String fromFlag = "";
	private String mUserId = "";
	private int mGroupId = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_list);

		mGroupList = new ArrayList<GroupBizBean>();
		mGroupListAdapter = new GroupListAdapter(this, mGroupList);
		// user id from sp
		mUserId = NSSharePreferenceKeeper.getStringValue(this,
				NSBizConstant.SPK_USER_ID, false);

		fromFlag = getIntent().getStringExtra(NSBizConstant.FROM_FLAG);
		initTitle();
		initView();
		initData();
	}

	private void initView() {
		mGroupListView = (ListView) findViewById(R.id.group_list_view);
		mGroupListView.setAdapter(mGroupListAdapter);

		mGroupListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				Intent intent = new Intent(GroupListActivity.this,
						MemberListActivity.class);
				if (NSBizConstant.FROM_FLAG_NOTICE.equals(fromFlag)) {
					intent.putExtra(NSBizConstant.FROM_FLAG,
							NSBizConstant.FROM_FLAG_NOTICE);
				}
				mGroupId =  mGroupList.get(arg2).getId();
				NSLog.d("mGroupId:" + mGroupId);
				intent.putExtra(NSBizConstant.KEY_GROUP_ID,mGroupId);
				startActivityForResult(intent,
						FLAG_ACTIVITY_REQUEST_MEMBER_LIST);
			}
		});

		mGroupListView
				.setOnItemLongClickListener(new OnItemLongClickListener() {
					@Override
					public boolean onItemLongClick(AdapterView<?> arg0,
							View arg1, int arg2, long arg3) {
						Toast.makeText(getApplicationContext(), arg3 + "",
								Toast.LENGTH_SHORT).show();
						return false;
					}
				});

	}

	private void initTitle() {
		mTitleLeftBtn = (TextView) findViewById(R.id.title_left_btn);
		mTitleLeftBtn.setClickable(true);
		mTitleRightBtn = (TextView) findViewById(R.id.title_right_btn);
		mTitleRightBtn.setClickable(true);
		if (NSBizConstant.FROM_FLAG_NOTICE.equals(fromFlag)) {
			mTitleRightBtn.setVisibility(View.INVISIBLE);
			mTitleLeftBtn.setVisibility(View.VISIBLE);
		} else {
			mTitleLeftBtn.setVisibility(View.INVISIBLE);
			mTitleRightBtn.setVisibility(View.VISIBLE);
		}

	}

	private void initData() {

		requestGroupList();
	}

	private void requestGroupList() {
		NSLog.i(TAG, "requestGroupList");
		showLoadingView();

		String msg;
		try {
			msg = NSMsgFactory.requestGroupListMsg(mUserId);
			postMessage(NSBizConstant.REQUEST_GET_GROUP_LIST,
					NSAppConfig.SERVER_URL + NSMsgFactory.CMD_GROUP_LIST,
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
		switch (requestID) {
		case NSBizConstant.REQUEST_GET_GROUP_LIST:

			try {
				JSONObject resultObj = new JSONObject(result);
				JSONObject paramsObj = resultObj
						.getJSONObject(NSBizConstant.KEY_PARAMS);
				JSONArray groupArray = paramsObj
						.getJSONArray(NSBizConstant.KEY_GROUP);
				Gson gson = new Gson();
				mGroupListFromWeb = gson.fromJson(groupArray.toString(),
						new TypeToken<List<GroupBizBean>>() {
						}.getType());
				mGroupList.addAll(mGroupListFromWeb);
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
		Intent intent = new Intent(GroupListActivity.this,
				GroupAddActivity.class);
		startActivityForResult(intent, FLAG_ACTIVITY_REQUEST_ADD_GROUP);
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
			case FLAG_ACTIVITY_REQUEST_MEMBER_LIST:

				Intent backIntent = data;
				int checkedCount = backIntent.getIntExtra(
						NSBizConstant.KEY_CHECKED_COUNT, 0);
				String checkArray = backIntent
						.getStringExtra(NSBizConstant.KEY_CHECKED_ARRAY_STR);
				backIntent.putExtra(NSBizConstant.KEY_GROUP_ID, mGroupId);
				NSLog.d("checkedCount:" + checkedCount);
				NSLog.d("checkArray:" + checkArray);
				setResult(RESULT_OK, backIntent);
				finish();
				break;

			default:
				break;
			}
		} else {
			setResult(RESULT_CANCELED);
		}

	}

}
