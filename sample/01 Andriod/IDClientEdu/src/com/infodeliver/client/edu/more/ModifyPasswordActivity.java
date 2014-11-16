package com.infodeliver.client.edu.more;

import org.json.JSONException;
import org.json.JSONObject;

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

public class ModifyPasswordActivity extends NSActivityBase {

    private static final String TAG = "ModifyPasswordActivity";

    private TextView mBackLabel;

    private EditText mOldPwdTxt;

    private EditText mPwdTxt;

    private EditText mConfirmPwdTxt;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        initTextView();
    }

    /**
     * init title
     */
    private void initTextView() {
        userId = NSSharePreferenceKeeper.getStringValue(this, NSBizConstant.SPK_USER_ID, false);
        mBackLabel = (TextView) findViewById(R.id.back_label);
        mBackLabel.setClickable(true);

        mOldPwdTxt= (EditText) findViewById(R.id.old_pwd_text);

        mPwdTxt= (EditText) findViewById(R.id.pwd_text);

        mConfirmPwdTxt= (EditText) findViewById(R.id.confirm_pwd_text);

    }

    @Override
    public void onError(int requestID, String errorCode, String errorDesc) {
        super.onError(requestID, errorCode, errorDesc);
        NSLog.i(TAG, "onError");
        NSLog.e(errorCode);
        showContentView();
        hideProgressDialog();
    }

    @Override
    public void onResult(int requestID, String result) {
        NSLog.i(TAG, "onResult");
        NSLog.d("result json", result);
        hideProgressDialog();
        switch (requestID) {
        case NSBizConstant.REQUEST_MODIFY_PWD :
            try {
                JSONObject json = new JSONObject(result);
                JSONObject rParams = (JSONObject) json.get(NSBizConstant.KEY_PARAMS);
                if (rParams.getBoolean("upd_flag")) {
                    NSSharePreferenceKeeper.keepStringValue(this, NSBizConstant.SPK_USER_PASS, rParams.getString("password"), false);
                    finish();
                } else if (rParams.getBoolean("old_pwd_err")) {
                    showToast("当前密码不正确");
                } else {
                    showToast("修改密码失败，请稍后再试");
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
        String oldPwd = mOldPwdTxt.getText().toString();
        String password = mPwdTxt.getText().toString();
        String confirmPwd = mConfirmPwdTxt.getText().toString();
        if (TextUtils.isEmpty(oldPwd)) {
            mOldPwdTxt.setSelection(mOldPwdTxt.getText().length());
            showToast("当前密码不能为空");
            return;
        } else if (TextUtils.isEmpty(password)) {
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
        showProgressDialog();
        requestModifyPwdJson(NSUtils.encryptMD5(oldPwd), NSUtils.encryptMD5(password));
    }

    private void requestModifyPwdJson(String oldPwd, String password) {
        NSLog.i(TAG, "requestModifyPwdJson");
        try {
            String msg = NSMsgFactory.reqModifyPwdMsg(userId, oldPwd, password);
            postMessage(NSBizConstant.REQUEST_MODIFY_PWD,
                    NSAppConfig.SERVER_URL + NSMsgFactory.CMD_MODIFY_PWD, NSHttpManager.POST, msg);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
