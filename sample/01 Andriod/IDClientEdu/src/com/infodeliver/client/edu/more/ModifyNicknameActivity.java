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

public class ModifyNicknameActivity extends NSActivityBase {

    private static final String TAG = "ModifyNicknameActivity";

    private TextView mBackLabel;

    private EditText mNicknameTxt;

    private String mNickname;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_nickname);
        initTextView();
    }

    /**
     * init title
     */
    private void initTextView() {
        userId = NSSharePreferenceKeeper.getStringValue(this, NSBizConstant.SPK_USER_ID, false);
        mBackLabel = (TextView) findViewById(R.id.back_label);
        mBackLabel.setClickable(true);

        mNickname = NSSharePreferenceKeeper.getStringValue(this, NSBizConstant.SPK_USER_NAME, false);
        mNicknameTxt= (EditText) findViewById(R.id.nickname_text);
        mNicknameTxt.setText(mNickname);
        mNicknameTxt.setSelection(mNicknameTxt.getText().length());
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
        case NSBizConstant.REQUEST_MODIFY_NICKNAME :
            try {
                JSONObject json = new JSONObject(result);
                JSONObject rParams = (JSONObject) json.get(NSBizConstant.KEY_PARAMS);
                if (rParams.getBoolean("upd_flag")){
                    Intent intent = getIntent();
                    intent.putExtra(NSBizConstant.KEY_NICKNAME, rParams.getString("nickname"));
                    setResult(RESULT_OK, intent);
                    NSSharePreferenceKeeper.keepStringValue(this, NSBizConstant.SPK_USER_NAME, rParams.getString("nickname"), false);
                    finish();
                } else {
                    showToast("修改昵称失败，请稍后再试");
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
        String nickname = mNicknameTxt.getText().toString();
        if (TextUtils.isEmpty(nickname)) {
            showToast("昵称不能为空");
            return;
        }
        showProgressDialog();
        requestModifyNicknameJson(nickname);
    }

    private void requestModifyNicknameJson(String nickname) {
        NSLog.i(TAG, "requestModifyNicknameJson");
        try {
            String msg = NSMsgFactory.reqModifyNicknameMsg(userId, nickname);
            postMessage(NSBizConstant.REQUEST_MODIFY_NICKNAME,
                    NSAppConfig.SERVER_URL + NSMsgFactory.CMD_MODIFY_NICKNAME, NSHttpManager.POST, msg);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
