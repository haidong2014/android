package com.infodeliver.client.edu.login;

import java.text.MessageFormat;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.infodeliver.client.base.NSActivityBase;
import com.infodeliver.client.edu.MainTabActivity;
import com.infodeliver.client.edu.R;
import com.infodeliver.client.utils.NSBizConstant;
import com.infodeliver.utils.NSLog;
import com.infodeliver.utils.NSSharePreferenceKeeper;
import com.infodeliver.utils.NSUtils;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.common.AccessTokenKeeper;
import com.sina.weibo.sdk.openapi.models.User;

public class WelcomeJoinActivity extends NSActivityBase {

    private static final String TAG = "WelcomeJoinActivity";

    private TextView mBackLabel;

    private TextView mDearUser;

    private TextView mNickname;

    private Button mJoinBtn;

    private User sinaUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_join);
        Intent intent = getIntent();
        sinaUser = (User) intent.getSerializableExtra(NSBizConstant.SINA_USER);
        initTextView();
        NSUtils.addActivity(this);
    }

    /**
     * init title
     */
    private void initTextView() {
        mBackLabel = (TextView) findViewById(R.id.back_label);
        mBackLabel.setClickable(true);

        mDearUser = (TextView) findViewById(R.id.dear_user);
        if (sinaUser != null) {
            mDearUser.setText(MessageFormat.format(getString(R.string.dear_user), getString(R.string.weibo_login_label)));
        }

        mNickname = (TextView) findViewById(R.id.nickname);
        if (sinaUser != null) {
            mNickname.setText(sinaUser.screen_name);
        }

        mJoinBtn = (Button) findViewById(R.id.join_btn);
        if (sinaUser != null) {
            mJoinBtn.setText(MessageFormat.format(getString(R.string.join_btn), getString(R.string.weibo_login_label)));
        }
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
        case NSBizConstant.REQUEST_LOGIN:
            try {
                JSONObject json = new JSONObject(result);
                JSONObject rParams = (JSONObject) json.get(NSBizConstant.KEY_PARAMS);
                if (rParams.getBoolean("is_login")){
                    Intent intent = new Intent(this,MainTabActivity.class);
                    startActivity(intent);
                    NSSharePreferenceKeeper.keepBooleanValue(this, NSBizConstant.SPK_ALREADY_LOGIN, true, false);
                    NSSharePreferenceKeeper.keepStringValue(this, NSBizConstant.SPK_USER_ID, rParams.getString("ID"), false);
                    NSSharePreferenceKeeper.keepStringValue(this,  NSBizConstant.SPK_USER_PASS, rParams.getString("USER_PASS"), false);
                    NSSharePreferenceKeeper.keepStringValue(this, NSBizConstant.SPK_USER_NAME, rParams.getString("USER_NAME"), false);
                    NSSharePreferenceKeeper.keepStringValue(this, NSBizConstant.SPK_USER_SEX, rParams.getString("USER_SEX"), false);
                    NSSharePreferenceKeeper.keepStringValue(this, NSBizConstant.SPK_USER_SIGN, rParams.getString("USER_SIGN"), false);
                    NSSharePreferenceKeeper.keepStringValue(this, NSBizConstant.SPK_USER_TEL, rParams.getString("USER_TEL"), false);
                    // SINA weibo
                    if (!TextUtils.isEmpty(rParams.getString("SINA_UID"))) {
                        Oauth2AccessToken token = new Oauth2AccessToken(rParams.getString("SINA_ACCESS_TOKEN"), rParams.getString("SINA_EXPIRES_IN"));
                        token.setUid(rParams.getString("SINA_UID"));
                        AccessTokenKeeper.writeAccessToken(WelcomeJoinActivity.this, token);
                    }

                    NSUtils.finishActivity();
                } else {
                    showContentView();
                    showToast("用户名或密码不正确。");
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

    public void doBack(View v) {
        NSLog.i(TAG, "doBack");
        finish();
    }
}
