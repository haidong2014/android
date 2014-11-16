/**
 * 
 */
package com.infodeliver.client.edu;

import android.os.Bundle;
import android.widget.TextView;

import com.infodeliver.client.base.NSActivityBase;
import com.infodeliver.utils.NSLog;

public class PushActivity extends NSActivityBase {
	private static final String TAG = "PushActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView textView=new TextView(this);
		textView.setText("PushActivity");
		setContentView(textView);
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
}
