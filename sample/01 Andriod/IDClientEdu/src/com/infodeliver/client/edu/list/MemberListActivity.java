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
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.infodeliver.client.base.NSActivityBase;
import com.infodeliver.client.edu.R;
import com.infodeliver.client.ui.MemberListAdapter;
import com.infodeliver.client.utils.NSBizConstant;
import com.infodeliver.client.utils.NSMsgFactory;
import com.infodeliver.db.domain.MemberBizBean;
import com.infodeliver.io.NSHttpManager;
import com.infodeliver.utils.NSAppConfig;
import com.infodeliver.utils.NSLog;
import com.infodeliver.utils.NSSharePreferenceKeeper;

/**
 * Member list
 * 
 * @author yujian
 * @version 1.0
 * @createDate 2014-07-28
 * @lastUpdate 2014-07-28
 */
public class MemberListActivity extends NSActivityBase {
	private static final String TAG = "MemberListActivity";

	private TextView mTitleLeftBtn;
	private TextView mTitleRightBtn;
	private TextView mTitleName;

	private ListView mMemberListView;
	private MemberListAdapter mMemberListAdapter;
	private ArrayList<MemberBizBean> mMemberList;
	private ArrayList<MemberBizBean> mMemberListFromWeb;
	
	private RelativeLayout mBottomLayout;
	
	private String mFromFlag = "";
	private int mGroupId;
	private String mUserId = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_member_list);

		mFromFlag = getIntent().getStringExtra(NSBizConstant.FROM_FLAG);
		mGroupId = getIntent().getIntExtra(NSBizConstant.KEY_GROUP_ID,-1);
		mUserId = NSSharePreferenceKeeper.getStringValue(this,
				NSBizConstant.SPK_USER_ID, false);
		if (mGroupId == -1) {
			showToast(getString(R.string.menber_get_fail_by_group_id));
			return;
		}
		mMemberList = new ArrayList<MemberBizBean>();
		mMemberListAdapter = new MemberListAdapter(this, mMemberList);
		initTitle();
		initView();
		initData();
	}

	private void initView() {
		mMemberListView = (ListView) findViewById(R.id.member_list_view);
		mMemberListView.setAdapter(mMemberListAdapter);
		if (NSBizConstant.FROM_FLAG_NOTICE.equals(mFromFlag)) {
			mBottomLayout = (RelativeLayout) findViewById(R.id.bottom_layout);
			mBottomLayout.setVisibility(View.VISIBLE);
			mMemberListAdapter.mCheckShowFlag = true;
		}
		mMemberListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				mMemberListView.setSelection(arg2);
				showToast(arg3 + "");
			}
		});

	}

	private void initTitle() {
		mTitleLeftBtn = (TextView) findViewById(R.id.title_left_btn);
		mTitleLeftBtn.setClickable(true);
		mTitleRightBtn = (TextView) findViewById(R.id.title_right_btn);
		mTitleRightBtn.setClickable(true);
		if (NSBizConstant.FROM_FLAG_NOTICE.equals(mFromFlag)) {
			mTitleRightBtn.setText(R.string.member_title_right_btn);
		} else {
			mTitleRightBtn.setText(R.string.member_title_right_btn_add);
		}
	}

	private void initData() {

		requestGroupMemberList();
		// for test
//		MemberBizBean grou1 = new MemberBizBean();
//		grou1.setId(1);
//		grou1.setMemberName("喜洋洋妈妈");
//		grou1.setChecked(true);
//		mMemberList.add(grou1);
//		MemberBizBean grou2 = new MemberBizBean();
//		grou2.setId(2);
//		grou2.setMemberName("灰太狼爸爸");
//		grou2.setChecked(true);
//		mMemberList.add(grou2);
//		MemberBizBean grou3 = new MemberBizBean();
//		grou3.setId(3);
//		grou3.setMemberName("小朴妈妈");
//		grou3.setChecked(true);
//		mMemberList.add(grou3);
//		MemberBizBean grou4 = new MemberBizBean();
//		grou4.setId(4);
//		grou4.setMemberName("向日葵妈妈");
//		grou4.setChecked(true);
//		mMemberList.add(grou4);
//		MemberBizBean grou5 = new MemberBizBean();
//		grou5.setId(5);
//		grou5.setMemberName("小李爸爸");
//		grou5.setChecked(true);
//		mMemberList.add(grou5);
//		MemberBizBean grou6 = new MemberBizBean();
//		grou6.setId(6);
//		grou6.setMemberName("小王爸爸");
//		grou6.setChecked(true);
//		mMemberList.add(grou6);
//		mMemberListAdapter.notifyDataSetChanged();
	}

	private void requestGroupMemberList() {
		NSLog.i(TAG, "requestGroupMemberList");
		showLoadingView();

		String msg;
		try {
			msg = NSMsgFactory.requestGroupMemberListMsg(mGroupId, mUserId);
			postMessage(NSBizConstant.REQUEST_GET_GROUP_LIST,
					NSAppConfig.SERVER_URL + NSMsgFactory.CMD_GROUP_MEMBER_LIST,
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
				JSONArray groupMemberArray = paramsObj
						.getJSONArray(NSBizConstant.KEY_GROUP_MEMBER);
				Gson gson = new Gson();
				mMemberListFromWeb= gson.fromJson(groupMemberArray.toString(),
						new TypeToken<List<MemberBizBean>>() {
						}.getType());
				mMemberList.addAll(mMemberListFromWeb);
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

	public void doCheckAll(View v) {

		CheckBox checkAll = (CheckBox) v;
		boolean checkFlg = checkAll.isChecked();

		for (int i = 0; i < mMemberList.size(); i++) {
			mMemberList.get(i).setChecked(checkFlg);
		}
		
		mMemberListAdapter.notifyDataSetChanged();
	}

	public void doRightClick (View v) {
		
		if (NSBizConstant.FROM_FLAG_NOTICE.equals(mFromFlag)) {
			StringBuffer idStr = new StringBuffer();
			int checkedCount = 0;
			for (int i = 0; i < mMemberList.size(); i++) {
				NSLog.d(mMemberList.get(i).getId() + ":" + mMemberList.get(i).isChecked());
				if (mMemberList.get(i).isChecked()) {
					checkedCount ++;
					idStr.append(mMemberList.get(i).getId()); 
					idStr.append(",");
				}
			}
			NSLog.d(idStr.toString());
			if (idStr.length() > 0) {
				idStr.deleteCharAt(idStr.length() - 1);
			}
			NSLog.d(idStr.toString());
			Intent intent = getIntent();
			intent.putExtra(NSBizConstant.KEY_CHECKED_COUNT, checkedCount);
			intent.putExtra(NSBizConstant.KEY_CHECKED_ARRAY_STR, idStr.toString());
			setResult(RESULT_OK, intent);
			finish();
		} else {
			showToast("add member");
		}
		
	}
	
	@Override
	protected void onResume() {
		NSLog.i(TAG, "onResume");
		super.onResume();

	}

}
