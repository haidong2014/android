package com.infodeliver.client.edu.login;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.infodeliver.client.base.NSActivityBase;
import com.infodeliver.client.edu.MainTabActivity;
import com.infodeliver.client.edu.R;
import com.infodeliver.client.utils.NSBizConstant;
import com.infodeliver.client.utils.NSMsgFactory;
import com.infodeliver.io.NSHttpManager;
import com.infodeliver.utils.NSAppConfig;
import com.infodeliver.utils.NSLog;
import com.infodeliver.utils.NSSharePreferenceKeeper;
import com.infodeliver.utils.NSUtils;

public class Register2Activity extends NSActivityBase {

    private static final String TAG = "Register2Activity";

    private TextView mBackLabel;

    private EditText mNicknameTxt;
    private EditText mPwdTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        initTextView();
        NSUtils.addActivity(this);
    }

    /**
     * init title
     */
    private void initTextView() {
        mBackLabel = (TextView) findViewById(R.id.back_label);
        mBackLabel.setClickable(true);

        mNicknameTxt= (EditText) findViewById(R.id.nickname_text);
        mPwdTxt= (EditText) findViewById(R.id.pwd_text);
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
        case NSBizConstant.REQUEST_REGISTER :
            try {
                JSONObject json = new JSONObject(result);
                JSONObject rParams = (JSONObject) json.get(NSBizConstant.KEY_PARAMS);
                if (rParams.getInt("register_flag") == 1) {
                    Intent intent = new Intent(this, MainTabActivity.class);
                    startActivity(intent);
                    NSSharePreferenceKeeper.keepBooleanValue(this, NSBizConstant.SPK_ALREADY_LOGIN, true, false);
                    NSSharePreferenceKeeper.keepStringValue(this, NSBizConstant.SPK_USER_ID, rParams.getString("ID"), false);
                    NSSharePreferenceKeeper.keepStringValue(this,  NSBizConstant.SPK_USER_PASS, rParams.getString("USER_PASS"), false);
                    NSSharePreferenceKeeper.keepStringValue(this, NSBizConstant.SPK_USER_NAME, rParams.getString("USER_NAME"), false);
                    NSSharePreferenceKeeper.keepStringValue(this, NSBizConstant.SPK_USER_SEX, rParams.getString("USER_SEX"), false);
                    NSSharePreferenceKeeper.keepStringValue(this, NSBizConstant.SPK_USER_SIGN, rParams.getString("USER_SIGN"), false);
                    NSSharePreferenceKeeper.keepStringValue(this, NSBizConstant.SPK_USER_TEL, rParams.getString("USER_TEL"), false);
                    NSUtils.finishActivity();
                } else {
                    showContentView();
                }
                showToast(rParams.getString("msg"));
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

    public void doSubmit(View v) {
        NSLog.i(TAG, "doSubmit");
        if (NSUtils.isFastDoubleClick()) {
            return;
        }
        closeInput();
        String password = mPwdTxt.getText().toString();
        String nickname = mNicknameTxt.getText().toString();
        if (TextUtils.isEmpty(password)) {
            showToast("密码不能为空");
            return;
        } else if (TextUtils.isEmpty(nickname)) {
            showToast("昵称不能为空");
            return;
        }
        showLoadingView();
        requestRegisterJson(nickname, NSUtils.encryptMD5(password));
    }

    private void requestRegisterJson(String nickname, String password) {
        NSLog.i(TAG, "requestRegisterJson");
        try {
            String phone  = getIntent().getStringExtra(NSBizConstant.PHONE);
            String msg = NSMsgFactory.reqRegisterMsg(phone, password, nickname);
            postMessage(NSBizConstant.REQUEST_REGISTER,
                    NSAppConfig.SERVER_URL + NSMsgFactory.CMD_REGISTER, NSHttpManager.POST, msg);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
