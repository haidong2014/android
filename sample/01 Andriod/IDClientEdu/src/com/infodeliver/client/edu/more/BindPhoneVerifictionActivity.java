package com.infodeliver.client.edu.more;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.infodeliver.client.base.NSActivityBase;
import com.infodeliver.client.edu.R;
import com.infodeliver.client.utils.NSBizConstant;
import com.infodeliver.client.utils.NSMsgFactory;
import com.infodeliver.io.NSHttpManager;
import com.infodeliver.utils.NSAppConfig;
import com.infodeliver.utils.NSLog;
import com.infodeliver.utils.NSSharePreferenceKeeper;
import com.infodeliver.utils.NSUtils;

public class BindPhoneVerifictionActivity extends NSActivityBase {

    private static final String TAG = "BindPhoneVerifictionActivity";

    private TextView mBackLabel;

    private TextView mBindPhoneLabel;

    private EditText mBindPhoneTxt;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_phone_verifiction);
        initTextView();
        NSUtils.addActivity(this);
    }

    /**
     * init title
     */
    private void initTextView() {
        userId = NSSharePreferenceKeeper.getStringValue(this, NSBizConstant.SPK_USER_ID, false);
        mBackLabel = (TextView) findViewById(R.id.back_label);
        mBackLabel.setClickable(true);

        mBindPhoneLabel = (TextView) findViewById(R.id.bind_phone_label);
        String phone = NSSharePreferenceKeeper.getStringValue(this, NSBizConstant.SPK_USER_TEL, false);
        phone = phone.substring(0,3) + "****" + phone.substring(7);
        mBindPhoneLabel.setText(phone);

        mBindPhoneTxt = (EditText) findViewById(R.id.bind_phone_text);
    }

    @Override
    public void onError(int requestID, String errorCode, String errorDesc) {
        super.onError(requestID, errorCode, errorDesc);
        NSLog.i(TAG, "onError");
        NSLog.e(errorCode);
        showContentView();
    }

    @Override
    public void onResult(int requestID, String result) {
        NSLog.i(TAG, "onResult");
        NSLog.d("result json", result);
        switch (requestID) {
        case NSBizConstant.REQUEST_CHECK_BIND_PHONE:
            try {
                JSONObject json = new JSONObject(result);
                JSONObject rParams = (JSONObject) json.get(NSBizConstant.KEY_PARAMS);
                if (rParams.getBoolean("check_flag")) {
                    Intent intent = new Intent();
                    intent.setClass(this, BindNewPhoneActivity.class);
                    startActivity(intent);
                } else {
                    showContentView();
                    showToast("绑定的手机号验证失败，请重新输入绑定的手机号。");
                }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void doBack(View v) {
        NSLog.i(TAG, "doBack");
        finish();
    }

    public void doVerification(View v) {
        NSLog.i(TAG, "doVerification");
        if (NSUtils.isFastDoubleClick()) {
            return;
        }
        closeInput();
        String bindPhone = mBindPhoneTxt.getText().toString();
        if (TextUtils.isEmpty(bindPhone)) {
            showToast("请输入你绑定的手机号");
            return;
        }
        showLoadingView();
        reqCheckBindPhoneJson(bindPhone);
    }

    private void reqCheckBindPhoneJson(String bindPhone) {
        NSLog.i(TAG, "reqBindPhoneVerificationJson");
        try {
            String msg = NSMsgFactory.reqCheckBindPhoneMsg(userId, bindPhone);
            postMessage(NSBizConstant.REQUEST_CHECK_BIND_PHONE,
                    NSAppConfig.SERVER_URL + NSMsgFactory.CMD_CHECK_BIND_PHONE,
                    NSHttpManager.POST, msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
