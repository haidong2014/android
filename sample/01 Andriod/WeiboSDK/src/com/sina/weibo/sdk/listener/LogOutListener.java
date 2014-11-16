package com.sina.weibo.sdk.listener;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;

import com.sina.weibo.sdk.common.AccessTokenKeeper;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

public class LogOutListener  implements RequestListener {

    private Context context;

    public LogOutListener(Context context){
        this.context = context;
    }

    @Override
    public void onComplete(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject obj = new JSONObject(response);
                String value = obj.getString("result");

                if ("true".equalsIgnoreCase(value)) {
                    AccessTokenKeeper.clear(context);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onWeiboException(WeiboException e) {
    }
}
