package com.infodeliver.client.edu.more;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

public class ModifySignActivity extends NSActivityBase {

    private static final String TAG = "ModifySignActivity";

    private static final String MAX_LENGTH = "30";

    private TextView mBackLabel;

    private TextView mSignCounter;

    private EditText mSignTxt;

    private String mSign;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_sign);
        initTextView();
    }

    /**
     * init title
     */
    private void initTextView() {
        userId = NSSharePreferenceKeeper.getStringValue(this, NSBizConstant.SPK_USER_ID, false);
        mBackLabel = (TextView) findViewById(R.id.back_label);
        mBackLabel.setClickable(true);

        mSignCounter = (TextView) findViewById(R.id.sign_counter);

        mSign = NSSharePreferenceKeeper.getStringValue(this, NSBizConstant.SPK_USER_SIGN, false);
        mSignTxt= (EditText) findViewById(R.id.sign_text);
        mSignTxt.setText(TextUtils.isEmpty(mSign) ? "" : mSign);
        mSignTxt.setSelection(mSignTxt.getText().length());
        mSignTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {
                String content = mSignTxt.getText().toString();
                mSignCounter.setText(content.length() + "/"
                        + MAX_LENGTH);
            }

        });
        mSignCounter.setText(mSignTxt.getText().length() + "/"
                + MAX_LENGTH);
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
        case NSBizConstant.REQUEST_MODIFY_SIGN :
            try {
                JSONObject json = new JSONObject(result);
                JSONObject rParams = (JSONObject) json.get(NSBizConstant.KEY_PARAMS);
                if (rParams.getBoolean("upd_flag")){
                    Intent intent = getIntent();
                    intent.putExtra(NSBizConstant.KEY_SIGN, rParams.getString("sign"));
                    setResult(RESULT_OK, intent);
                    NSSharePreferenceKeeper.keepStringValue(this, NSBizConstant.SPK_USER_SIGN, rParams.getString("sign"), false);
                    finish();
                } else {
                    showToast("修改个人签名失败，请稍后再试");
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

    public void doSave(View v) {
        NSLog.i(TAG, "doSave");
        if (NSUtils.isFastDoubleClick()) {
            return;
        }
        closeInput();
        String sign = mSignTxt.getText().toString();
        showProgressDialog();
        requestModifySignJson(sign);
    }

    private void requestModifySignJson(String sign) {
        NSLog.i(TAG, "requestModifySignJson");
        try {
            String msg = NSMsgFactory.reqModifySignMsg(userId, sign);
            postMessage(NSBizConstant.REQUEST_MODIFY_SIGN,
                    NSAppConfig.SERVER_URL + NSMsgFactory.CMD_MODIFY_SIGN, NSHttpManager.POST, msg);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
