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
import com.infodeliver.client.edu.R;
import com.infodeliver.client.utils.NSBizConstant;
import com.infodeliver.client.utils.NSMsgFactory;
import com.infodeliver.io.NSHttpManager;
import com.infodeliver.utils.NSAppConfig;
import com.infodeliver.utils.NSLog;
import com.infodeliver.utils.NSUtils;

public class PasswordResetActivity extends NSActivityBase {

    private static final String TAG = "PasswordResetActivity";

//    private Button mConfirmButton;

    private TextView mBackLabel;

    private EditText mConfirmPwdTxt;
    private EditText mPwdTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);
        initTextView();
        NSUtils.addActivity(this);
    }

    /**
     * init title
     */
    private void initTextView() {
        mBackLabel = (TextView) findViewById(R.id.back_label);
        mBackLabel.setClickable(true);

        mConfirmPwdTxt= (EditText) findViewById(R.id.confirm_pwd_text);

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
        showContentView();
        switch (requestID) {
            case NSBizConstant.REQUEST_PASSWORD_RESET:
                try {
                    JSONObject json = new JSONObject(result);
                    JSONObject rParams = (JSONObject) json.get(NSBizConstant.KEY_PARAMS);
                    if (rParams.getBoolean("is_reset")){
                        Intent intent = new Intent(this,LoginActivity.class);
                        startActivity(intent);
                    } else {
                        showToast("密码重置失败，请稍后重试。");
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


    public void doConfirm(View v) {
        NSLog.i(TAG, "doConfirm");
        if (NSUtils.isFastDoubleClick()) {
            return;
        }
        closeInput();
        String password = mPwdTxt.getText().toString();
        String confirmPwd = mConfirmPwdTxt.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPwdTxt.setSelection(mPwdTxt.getText().length());
            showToast("新密码不能为空");
            return;
        } else if (TextUtils.isEmpty(confirmPwd)) {
            mConfirmPwdTxt.setSelection(mConfirmPwdTxt.getText().length());
            showToast("确认新密码不能为空");
            return;
        } else if (!password.equals(confirmPwd)) {
            mPwdTxt.setSelection(mPwdTxt.getText().length());
            showToast("两次输入的新密码不一致，请重新输入");
            return;
        }
        showLoadingView();
        requestPasswordResetJson(NSUtils.encryptMD5(password));
    }

    private void requestPasswordResetJson(String password) {
        NSLog.i(TAG, "requestRegisterJson");
        try {
            String phone  = getIntent().getStringExtra(NSBizConstant.PHONE);
            String msg = NSMsgFactory.reqPasswordResetMsg(phone, password);
            postMessage(NSBizConstant.REQUEST_PASSWORD_RESET,
                    NSAppConfig.SERVER_URL + NSMsgFactory.CMD_PASSWORD_RESET, NSHttpManager.POST, msg);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
