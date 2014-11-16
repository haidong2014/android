/**
 * 
 */
package com.infodeliver.client.edu.reply;

import java.util.ArrayList;

import org.json.JSONException;

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
import com.infodeliver.client.utils.NSMsgFactory;
import com.infodeliver.db.domain.MemberBizBean;
import com.infodeliver.db.domain.ReplyBizBean;
import com.infodeliver.io.NSHttpManager;
import com.infodeliver.utils.NSAppConfig;
import com.infodeliver.utils.NSLog;
import com.infodeliver.utils.NSSharePreferenceKeeper;

/**
 * Reply
 * 
 * @author yujian
 * @version 1.0
 * @createDate 2014-08-21
 * @lastUpdate 2014-08-21
 */
public class ReplyActivity extends NSActivityBase {
	private static final String TAG = "ReplyActivity";

	private TextView mTitleLeftBtn;
	private TextView mTitleRightBtn;
	
	private EditText mReplyText;
	
	private ReplyBizBean mReply;
	private int mNoticeId;
	
	private String mUserId = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reply_add);

		mUserId = NSSharePreferenceKeeper.getStringValue(this,
				NSBizConstant.SPK_USER_ID, false);
		mNoticeId = getIntent().getIntExtra(NSBizConstant.KEY_NOTICES_ID, -1);
		mReply = new ReplyBizBean();
		mReply.setNoticeId(mNoticeId);
		mReply.setReplyUserId(mUserId);
		initTitle();
		initView();
	}

	private void initView() {
		mReplyText = (EditText) findViewById(R.id.reply_text);
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
		switch (requestID) {
		case NSBizConstant.REQUEST_SAVE_REPLY:
			showToast("Save fail");
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
		case NSBizConstant.REQUEST_SAVE_REPLY:
			setResult(RESULT_OK);
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
	}

	public void doLeftClick(View v) {
		finish();
	}

	public void doRightClick (View v) {
		if(doCheckForm()){
			//do save
			saveReplyToWeb();
		}
	}
	
	private boolean doCheckForm() {
		if (TextUtils.isEmpty(mReplyText.getText().toString())) {
			showToast("Need:" + mReplyText.getHint());
			return false;
		}  else {
			mReply.setReplyText(mReplyText.getText().toString());
		}
		return true;
	}
	
	@Override
	protected void onResume() {
		NSLog.i(TAG, "onResume");
		super.onResume();

	}

	private void saveReplyToWeb() {
		NSLog.i(TAG, "saveReplyToWeb");
		showLoadingView();

		String msg;
		try {
			msg = NSMsgFactory.requestSaveReplyMsg(mReply);
			postMessage(NSBizConstant.REQUEST_SAVE_REPLY,
					NSAppConfig.SERVER_URL + NSMsgFactory.CMD_REPLY_SAVE,
					NSHttpManager.POST, msg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
}
