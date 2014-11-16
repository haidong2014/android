package com.infodeliver.client.edu.login;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.common.AccessTokenKeeper;
import com.sina.weibo.sdk.common.Constants;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.listener.AuthListener;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.models.ErrorInfo;
import com.sina.weibo.sdk.openapi.models.User;
import com.sina.weibo.sdk.utils.LogUtil;

public class LoginActivity extends NSActivityBase {

    private static final String TAG = "LoginActivity";

    private static final int FLAG_ACTIVITY_REQUEST_SINA_LOGIN = 32973;

    private TextView mRegisterLabel;
    private TextView mForgetLabel;

    private TextView mBackLabel;

    private EditText mPhoneTxt;
    private EditText mPwdTxt;

    private TextView mWBLoginLabel;

    /** 微博 Web 授权类，提供登陆等功能  */
    private WeiboAuth mWeiboAuth;

    /** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
    private SsoHandler mSsoHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        NSLog.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initTextView();
        NSUtils.addActivity(this);
    }

    /**
     * init title
     */
    private void initTextView() {

        mRegisterLabel = (TextView) findViewById(R.id.register_label);
        mRegisterLabel.setClickable(true);

        mForgetLabel = (TextView) findViewById(R.id.forget_label);
        mForgetLabel.setClickable(true);

        mBackLabel = (TextView) findViewById(R.id.back_label);
        mBackLabel.setClickable(true);
        mBackLabel.setVisibility(View.INVISIBLE);

        mPhoneTxt= (EditText) findViewById(R.id.phone_text);

        mPwdTxt= (EditText) findViewById(R.id.pwd_text);

        mWBLoginLabel = (TextView) findViewById(R.id.weibo_login_label);
        mWBLoginLabel.setClickable(true);

        // 创建微博实例
        mWeiboAuth = new WeiboAuth(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
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
                        AccessTokenKeeper.writeAccessToken(LoginActivity.this, token);
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

    public void doForget(View v) {
        NSLog.i(TAG, "doForget");
        Intent intent = new Intent(this,RegisterActivity.class);
        intent.putExtra(NSBizConstant.FORGET_FLAG, 1);
        startActivity(intent);
    }

    public void doRegister(View v) {
        NSLog.i(TAG, "doRegister");
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    public void doBack(View v) {
        NSLog.i(TAG, "doBack");
        finish();
    }

    public void doLogin(View v) {
        NSLog.i(TAG, "doLogin");
        if (NSUtils.isFastDoubleClick()) {
            return;
        }
        closeInput();
        String phone = mPhoneTxt.getText().toString();
        String password = mPwdTxt.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            showToast("请输入手机号。");
            return;
        } else if (TextUtils.isEmpty(password)) {
            showToast("请输入密码。");
            return;
        }
        showLoadingView();
        requestLoginJson(phone, NSUtils.encryptMD5(password));
    }

    private void requestLoginJson(String phone, String password) {
        NSLog.i(TAG, "requestLoginJson");
        try {
            String msg = NSMsgFactory.requestLoginMsg(phone,password);
            postMessage(NSBizConstant.REQUEST_LOGIN,
                    NSAppConfig.SERVER_URL + NSMsgFactory.CMD_LOGIN, NSHttpManager.POST, msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void doWeiboAuth(View v) {
        NSLog.i(TAG, "doWeiboAuth");
        // SSO 授权
        mSsoHandler = new SsoHandler(LoginActivity.this, mWeiboAuth);
        mSsoHandler.authorize(new AuthListener(LoginActivity.this));
    }

    /**
     * 当 SSO 授权 Activity 退出时，该函数被调用。
     *
     * @see {@link Activity#onActivityResult}
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
            case FLAG_ACTIVITY_REQUEST_SINA_LOGIN:
                // SSO 授权回调
                // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResult
                if (mSsoHandler != null) {
                    mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
                }

                Oauth2AccessToken mAccessToken = AccessTokenKeeper.readAccessToken(LoginActivity.this);
                if (mAccessToken.isSessionValid()) {
                    showProgressDialog();
                    // 获取用户信息接口
                    UsersAPI mUsersAPI = new UsersAPI(mAccessToken);
                    long uid = Long.parseLong(mAccessToken.getUid());
                    mUsersAPI.show(uid, mListener);
                }
                break;
            default:
                break;
            }
        } else {
            setResult(RESULT_CANCELED);
        }
    }

    /**
     * 微博 OpenAPI 回调接口。
     */
    private RequestListener mListener = new RequestListener() {
        @Override
        public void onComplete(String response) {
            if (!TextUtils.isEmpty(response)) {
                LogUtil.i(TAG, response);
                // 调用 User#parse 将JSON串解析成User对象
                User user = User.parse(response);
                if (user != null) {
                    Intent intent = new Intent(LoginActivity.this, WelcomeJoinActivity.class);
                    intent.putExtra(NSBizConstant.SINA_USER, user);
                    startActivity(intent);
                    hideProgressDialog();
                    Toast.makeText(LoginActivity.this,
                            "获取User信息成功，用户昵称：" + user.screen_name,
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this, response, Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
            LogUtil.e(TAG, e.getMessage());
            ErrorInfo info = ErrorInfo.parse(e.getMessage());
            Toast.makeText(LoginActivity.this, info.toString(), Toast.LENGTH_LONG).show();
        }
    };
}
