package com.infodeliver.client.edu.more;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.infodeliver.client.base.NSActivityBase;
import com.infodeliver.client.edu.R;
import com.infodeliver.utils.NSLog;
import com.infodeliver.utils.NSUtils;

public class VerificationChangeActivity extends NSActivityBase {

    private static final String TAG = "VerificationChangeActivity";

    private TextView mBackLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_change);
        initTextView();
        NSUtils.addActivity(this);
    }

    /**
     * init title
     */
    private void initTextView() {
        mBackLabel = (TextView) findViewById(R.id.back_label);
        mBackLabel.setClickable(true);
    }

    @Override
    public void onError(int requestID, String errorCode, String errorDesc) {
        super.onError(requestID, errorCode, errorDesc);
        NSLog.i(TAG, "onError");
        NSLog.e(errorCode);
        showContentView();
    }

    @Override
    protected void onReloadContent() {
        NSLog.i(TAG, "onReloadContent");
        super.onReloadContent();
    }

    public void doBack(View v) {
        NSLog.i(TAG, "doBack");
        finish();
    }

    public void doBindPhoneVerifiction(View v) {
        NSLog.i(TAG, "doBindPhoneVerifiction");
        Intent intent = new Intent(this,BindPhoneVerifictionActivity.class);
        startActivity(intent);
    }

    public void doPhoneVerifiction(View v) {
        NSLog.i(TAG, "doPhoneVerifiction");
        Intent intent = new Intent(this,SafetyVerificationActivity.class);
        startActivity(intent);
    }
}
