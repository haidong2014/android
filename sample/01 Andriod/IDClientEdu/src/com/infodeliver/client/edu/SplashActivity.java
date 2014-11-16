package com.infodeliver.client.edu;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.infodeliver.client.base.NSActivityBase;
import com.infodeliver.client.edu.login.LoginActivity;
import com.infodeliver.client.edu.login.RegisterActivity;
import com.infodeliver.client.utils.NSBizConstant;
import com.infodeliver.client.utils.NSMsgFactory;
import com.infodeliver.io.NSHttpManager;
import com.infodeliver.utils.NSAppConfig;
import com.infodeliver.utils.NSLog;
import com.infodeliver.utils.NSSharePreferenceKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.common.AccessTokenKeeper;

public class SplashActivity extends NSActivityBase {

    private static final String TAG = "SplashActivity";

    private final int SPLASH_DISPLAY_LENGHT = 6000; // 延迟六秒

    private long exitTime = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        NSLog.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                boolean alreadyLogin = NSSharePreferenceKeeper.getBooleanValue(SplashActivity.this, NSBizConstant.SPK_ALREADY_LOGIN, false, false);
                if (alreadyLogin) {
                    requestGetUserJson();
                } else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

        }, SPLASH_DISPLAY_LENGHT);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                showToast("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
        case NSBizConstant.REQUEST_GET_USER:
            try {
                JSONObject json = new JSONObject(result);
                JSONObject rParams = (JSONObject) json.get(NSBizConstant.KEY_PARAMS);
                if (!rParams.isNull("ID")) {
                    NSSharePreferenceKeeper.keepStringValue(this, NSBizConstant.SPK_USER_NAME, rParams.getString("USER_NAME"), false);
                    NSSharePreferenceKeeper.keepStringValue(this, NSBizConstant.SPK_USER_SEX, rParams.getString("USER_SEX"), false);
                    NSSharePreferenceKeeper.keepStringValue(this, NSBizConstant.SPK_USER_SIGN, rParams.getString("USER_SIGN"), false);
                    NSSharePreferenceKeeper.keepStringValue(this, NSBizConstant.SPK_USER_TEL, rParams.getString("USER_TEL"), false);
                    // SINA weibo
                    if (!TextUtils.isEmpty(rParams.getString("SINA_UID"))) {
                        Oauth2AccessToken token = new Oauth2AccessToken(rParams.getString("SINA_ACCESS_TOKEN"), rParams.getString("SINA_EXPIRES_IN"));
                        token.setUid(rParams.getString("SINA_UID"));
                        AccessTokenKeeper.writeAccessToken(SplashActivity.this, token);
                    }
                    Intent intent = new Intent(this,MainTabActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    finish();
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

    public void weiboLogin(View v) {
        NSLog.i(TAG, "weiboLogin");

    }

    public void qqLogin(View v) {
        NSLog.i(TAG, "qqLogin");

    }

    public void skipRegister(View v) {
        NSLog.i(TAG, "skipRegister");
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void skipLogin(View v) {
        NSLog.i(TAG, "skipLogin");
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


    private void requestGetUserJson() {
        NSLog.i(TAG, "requestGetUserJson");
        try {
            String userId = NSSharePreferenceKeeper.getStringValue(this, NSBizConstant.SPK_USER_ID, false);
            String pwd = NSSharePreferenceKeeper.getStringValue(this, NSBizConstant.SPK_USER_PASS, false);
            String msg = NSMsgFactory.reqGetUserMsg(userId, pwd);
            postMessage(NSBizConstant.REQUEST_GET_USER,
                    NSAppConfig.SERVER_URL + NSMsgFactory.CMD_GET_USER, NSHttpManager.POST, msg);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
