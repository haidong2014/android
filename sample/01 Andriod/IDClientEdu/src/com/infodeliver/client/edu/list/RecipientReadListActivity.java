/**
 * 
 */
package com.infodeliver.client.edu.list;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.infodeliver.client.base.NSActivityBase;
import com.infodeliver.client.edu.R;
import com.infodeliver.client.ui.RecipientReadListAdapter;
import com.infodeliver.client.utils.NSBizConstant;
import com.infodeliver.client.utils.NSMsgFactory;
import com.infodeliver.db.domain.NoticeBizBean;
import com.infodeliver.db.domain.RecipientBizBean;
import com.infodeliver.io.NSHttpManager;
import com.infodeliver.utils.NSAppConfig;
import com.infodeliver.utils.NSLog;

/**
 * Group list
 * 
 * @author yujian
 * @version 1.0
 * @createDate 2014-08-07
 * @lastUpdate 2014-08-07
 */
public class RecipientReadListActivity extends NSActivityBase {
	private static final String TAG = "RecipientReadListActivity";
	
	private TextView mTitleLeftBtn;
	private ListView mRecipientListView;
	private RecipientReadListAdapter mRecipientReadListAdapter;
	private ArrayList<RecipientBizBean> mRecipientList;
	private ArrayList<RecipientBizBean> mRecipientListFromWeb;
	
	private int mNoticeId;
	private String mCreatorId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipient_read_list);

		mRecipientList = new ArrayList<RecipientBizBean>();
		mRecipientReadListAdapter = new RecipientReadListAdapter(this, mRecipientList);
		
		mNoticeId = getIntent().getIntExtra(NSBizConstant.KEY_NOTICES_ID, 0);
		mCreatorId = getIntent().getStringExtra(NSBizConstant.KEY_NOTICES_CREATOR_ID);
				
		initTitle();
		initView();
		initData();
	}


	private void initView() {
		mRecipientListView = (ListView) findViewById(R.id.recipient_read_list_view);
		mRecipientListView.setAdapter(mRecipientReadListAdapter);
	}

	private void initTitle() {
		mTitleLeftBtn = (TextView) findViewById(R.id.title_left_btn);
		mTitleLeftBtn.setClickable(true);
	}
	
	private void initData() {
		
		requestRecipientFromWeb();
	}
	
	private void requestRecipientFromWeb() {
		NSLog.i(TAG, "requestRecipientFromWeb");
		showLoadingView();
		String msg;
		try {
			msg = NSMsgFactory.requestRecipientStatusListMsg(mNoticeId, mCreatorId);
			postMessage(NSBizConstant.REQUEST_RECIPIENT_LIST,
					NSAppConfig.SERVER_URL + NSMsgFactory.CMD_NOTICE_GET_RECIPIENT_STATUS_LIST,
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
		case NSBizConstant.REQUEST_RECIPIENT_LIST:
			
			JSONObject resultObj = new JSONObject();
			JSONObject paramsObj = new JSONObject();
			Gson gson = new Gson();
			JSONArray tempArray;
			try {
				resultObj = new JSONObject(result);
				paramsObj = resultObj
						.getJSONObject(NSBizConstant.KEY_PARAMS);
				
				tempArray = paramsObj.getJSONArray(NSBizConstant.KEY_RECIPIENT_LIST);
				mRecipientListFromWeb = gson.fromJson(tempArray.toString(),
						new TypeToken<List<RecipientBizBean>>() {
						}.getType());
				
				mRecipientList.addAll(mRecipientListFromWeb);
				mRecipientReadListAdapter.notifyDataSetChanged();
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
		showToast("add group");
	}

	@Override
	protected void onResume() {
		NSLog.i(TAG, "onResume");
		super.onResume();

	}
	
}
