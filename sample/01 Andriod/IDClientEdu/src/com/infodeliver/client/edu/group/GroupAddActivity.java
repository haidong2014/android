/**
 * 
 */
package com.infodeliver.client.edu.group;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.infodeliver.client.base.NSActivityBase;
import com.infodeliver.client.edu.R;
import com.infodeliver.client.ui.MemberListAdapter;
import com.infodeliver.client.utils.NSBizConstant;
import com.infodeliver.db.domain.MemberBizBean;
import com.infodeliver.utils.NSLog;

/**
 * Group Add
 * 
 * @author yujian
 * @version 1.0
 * @createDate 2014-08-19
 * @lastUpdate 2014-08-19
 */
public class GroupAddActivity extends NSActivityBase {
	private static final String TAG = "GroupAddActivity";

	private TextView mTitleLeftBtn;
	private TextView mTitleRightBtn;
	
	private EditText mGroupNameET;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_add);

		initTitle();
		initView();
	}

	private void initView() {
		mGroupNameET = (EditText) findViewById(R.id.group_name);
	}

	private void initTitle() {
		mTitleLeftBtn = (TextView) findViewById(R.id.title_left_btn);
		mTitleLeftBtn.setClickable(true);
		mTitleRightBtn = (TextView) findViewById(R.id.title_right_btn);
		mTitleRightBtn.setClickable(true);
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
	}

	@Override
	protected void onReloadContent() {
		NSLog.i(TAG, "onReloadContent");
		super.onReloadContent();
	}

	public void doLeftClick(View v) {
		finish();
	}


	public void doRightClick (View v) {
		if(doCheckForm()){
			//do save
			
		}
	}
	
	private boolean doCheckForm() {
		if (TextUtils.isEmpty(mGroupNameET.getText().toString())) {
			showToast("Need:" + mGroupNameET.getHint());
			return false;
		} 
		return true;
	}
	
	@Override
	protected void onResume() {
		NSLog.i(TAG, "onResume");
		super.onResume();

	}

}
