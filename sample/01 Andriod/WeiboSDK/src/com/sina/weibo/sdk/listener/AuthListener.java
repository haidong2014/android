package com.sina.weibo.sdk.listener;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.sina.weibo.sdk.R;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.common.AccessTokenKeeper;
import com.sina.weibo.sdk.exception.WeiboException;

public class AuthListener implements WeiboAuthListener {


    private Context context;

    /** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能  */
    private Oauth2AccessToken mAccessToken;

    public AuthListener(Context context){
        this.context = context;
    }

    @Override
    public void onComplete(Bundle values) {
        // 从 Bundle 中解析 Token
        mAccessToken = Oauth2AccessToken.parseAccessToken(values);
        if (mAccessToken.isSessionValid()) {
            // 显示 Token
            updateTokenView(false);

            // 保存 Token 到 SharedPreferences
            AccessTokenKeeper.writeAccessToken(context, mAccessToken);
            Toast.makeText(context, R.string.weibosdk_demo_toast_auth_success, Toast.LENGTH_SHORT).show();
        } else {
            // 以下几种情况，您会收到 Code：
            // 1. 当您未在平台上注册的应用程序的包名与签名时；
            // 2. 当您注册的应用程序包名与签名不正确时；
            // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
            String code = values.getString("code");
            String message = context.getString(R.string.weibosdk_demo_toast_auth_failed);
            if (!TextUtils.isEmpty(code)) {
                message = message + "\nObtained the code: " + code;
            }
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCancel() {
        Toast.makeText(context,
                R.string.weibosdk_demo_toast_auth_canceled, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onWeiboException(WeiboException e) {
        Toast.makeText(context,
                "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
    }

    /**
     * 显示当前 Token 信息。
     *
     * @param hasExisted 配置文件中是否已存在 token 信息并且合法
     */
    private void updateTokenView(boolean hasExisted) {
        String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US).format(
                new java.util.Date(mAccessToken.getExpiresTime()));
        String format = context.getString(R.string.weibosdk_demo_token_to_string_format_1);

        String message = String.format(format, mAccessToken.getToken(), date);
        if (hasExisted) {
            message = context.getString(R.string.weibosdk_demo_token_has_existed) + "\n" + message;
        }
    }
}
