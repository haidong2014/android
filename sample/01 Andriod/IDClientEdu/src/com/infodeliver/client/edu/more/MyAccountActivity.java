package com.infodeliver.client.edu.more;

import java.text.SimpleDateFormat;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.infodeliver.client.base.NSActivityBase;
import com.infodeliver.client.base.NSApplication;
import com.infodeliver.client.edu.R;
import com.infodeliver.client.utils.NSBizConstant;
import com.infodeliver.client.utils.NSMsgFactory;
import com.infodeliver.io.NSHttpManager;
import com.infodeliver.utils.NSAppConfig;
import com.infodeliver.utils.NSLog;
import com.infodeliver.utils.NSSharePreferenceKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.common.AccessTokenKeeper;
import com.sina.weibo.sdk.common.Constants;
import com.sina.weibo.sdk.listener.AuthListener;
import com.sina.weibo.sdk.listener.LogOutListener;
import com.sina.weibo.sdk.openapi.LogoutAPI;

public class MyAccountActivity extends NSActivityBase {
    private static final String TAG = "MyAccountActivity";

    private static final int FLAG_ACTIVITY_REQUEST_NICKNAME_MODIFY = 1;

    private static final int FLAG_ACTIVITY_REQUEST_SIGN_MODIFY = 2;

    private static final int FLAG_ACTIVITY_REQUEST_SINA_LOGIN = 32973;

    private TextView mBackLabel;

    private TextView mNickname;
    private TextView mSex;
    private TextView mSignature;
    private TextView mPhone;

    private Button mWeiboBtn;

    AlertDialog alertdialog;

    private String userId;

    /** 微博 Web 授权类，提供登陆等功能  */
    private WeiboAuth mWeiboAuth;

    /** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
    private SsoHandler mSsoHandler;

    private Oauth2AccessToken mAccessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        initTextView();
    }

    /**
     * init title
     */
    private void initTextView() {
        userId = NSSharePreferenceKeeper.getStringValue(this, NSBizConstant.SPK_USER_ID, false);
        mBackLabel = (TextView) findViewById(R.id.back_label);
        mBackLabel.setClickable(true);

        mNickname = (TextView) findViewById(R.id.nickname);
        mNickname.setText(NSSharePreferenceKeeper.getStringValue(this, NSBizConstant.SPK_USER_NAME, false));

        mSex = (TextView) findViewById(R.id.sex);
        mSex.setText(getSex(NSSharePreferenceKeeper.getStringValue(this, NSBizConstant.SPK_USER_SEX, false)));

        mSignature = (TextView) findViewById(R.id.signature);
        mSignature.setText(getSign(NSSharePreferenceKeeper.getStringValue(this, NSBizConstant.SPK_USER_SIGN, false)));

        mPhone = (TextView) findViewById(R.id.phone);
        String phone = NSSharePreferenceKeeper.getStringValue(this, NSBizConstant.SPK_USER_TEL, false);
        phone = phone.substring(0,3) + "****" + phone.substring(7);
        ((NSApplication) getApplication()).setPhone(phone);
//        mPhone.setText(phone);

        mAccessToken = AccessTokenKeeper.readAccessToken(MyAccountActivity.this);

        mWeiboBtn = (Button) findViewById(R.id.weibo_btn);
        if (mAccessToken.isSessionValid()) {
             mWeiboBtn.setText(R.string.unbind);
        } else {
             mWeiboBtn.setText(R.string.bind);
        }

        // 创建微博实例
        mWeiboAuth = new WeiboAuth(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
    }

    @Override
    public void onError(int requestID, String errorCode, String errorDesc) {
        super.onError(requestID, errorCode, errorDesc);
        NSLog.i(TAG, "onError");
        NSLog.e(errorCode);
        hideProgressDialog();
        showContentView();
        switch (requestID) {
        case NSBizConstant.REQUEST_BIND_SINA:
            new LogoutAPI(AccessTokenKeeper.readAccessToken(MyAccountActivity.this)).logout(new LogOutListener(MyAccountActivity.this));
            break;
        default:
            break;
        }
    }

    @Override
    public void onResult(int requestID, String result) {
        NSLog.i(TAG, "onResult");
        NSLog.d("result json", result);
        hideProgressDialog();
        switch (requestID) {
        case NSBizConstant.REQUEST_SET_SEX:
            try {
                JSONObject json = new JSONObject(result);
                JSONObject rParams = (JSONObject) json.get(NSBizConstant.KEY_PARAMS);
                if (rParams.getBoolean("upd_flag")){
                    Message msg = mUIHandler.obtainMessage(2);
                    msg.obj = rParams.getString("sex");
                    msg.sendToTarget();
                    NSSharePreferenceKeeper.keepStringValue(this, NSBizConstant.SPK_USER_SEX, rParams.getString("sex"), false);
                } else {
                    showToast("设置失败，请稍后再试");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            break;
        case NSBizConstant.REQUEST_BIND_SINA:
            try {
                JSONObject json = new JSONObject(result);
                JSONObject rParams = (JSONObject) json.get(NSBizConstant.KEY_PARAMS);
                if (rParams.getBoolean("bind_flag")){
                    mWeiboBtn.setText(R.string.unbind);
                } else {
                    new LogoutAPI(AccessTokenKeeper.readAccessToken(MyAccountActivity.this)).logout(new LogOutListener(MyAccountActivity.this));
                    showToast("绑定失败，请稍后再试");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            break;
        case NSBizConstant.REQUEST_UNBIND_SINA:
            try {
                JSONObject json = new JSONObject(result);
                JSONObject rParams = (JSONObject) json.get(NSBizConstant.KEY_PARAMS);
                if (rParams.getBoolean("bind_flag")){
                    new LogoutAPI(AccessTokenKeeper.readAccessToken(MyAccountActivity.this)).logout(new LogOutListener(MyAccountActivity.this));
                    mWeiboBtn.setText(R.string.bind);
                } else {
                    showToast("取消绑定失败，请稍后再试");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            break;
        default:
            break;
        }
    }

    private Handler mUIHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: {
                    break;
                }
                case 2: {
                    String code = (String) msg.obj;
                    mSex.setText(getSex(code));
                    break;
                }
            }
        }
    };

    private String getSign(String sign){
        return TextUtils.isEmpty(sign) ? "未填写" : sign;
    }

    private String getSex(String sex){
        if ("0".equals(sex)){
            return "女";
        } else if ("1".equals(sex)){
            return "男";
        } else {
            return "";
        }
    }

    @Override
    protected void onReloadContent() {
        NSLog.i(TAG, "onReloadContent");
        super.onReloadContent();
    }

    public void doBack(View v) {
        NSLog.i(TAG, "doBack");
        String userName = NSSharePreferenceKeeper.getStringValue(this, NSBizConstant.SPK_USER_NAME, false);
        Intent intent = getIntent();
        intent.putExtra(NSBizConstant.KEY_NICKNAME, userName);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void setNickname(View v) {
        NSLog.i(TAG, "setNickname");
        Intent intent = new Intent(this,ModifyNicknameActivity.class);
        startActivityForResult(intent, FLAG_ACTIVITY_REQUEST_NICKNAME_MODIFY);
    }

    public void setPortrait(View v) {
        NSLog.i(TAG, "setPortrait");
        showToast("setPortrait");

    }

    public void setSignature(View v) {
        NSLog.i(TAG, "setSignature");
        Intent intent = new Intent(this,ModifySignActivity.class);
        startActivityForResult(intent, FLAG_ACTIVITY_REQUEST_SIGN_MODIFY);
    }

    public void setPasswordModify(View v) {
        NSLog.i(TAG, "setPasswordModify");
        Intent intent = new Intent(this,ModifyPasswordActivity.class);
        startActivity(intent);
    }

    public void setPhone(View v) {
        NSLog.i(TAG, "setPhone");
        Intent intent = new Intent(this,SafetyVerificationActivity.class);
        startActivity(intent);
    }

    public void setWeibo(View v) {
        NSLog.i(TAG, "setPhone");
        mAccessToken = AccessTokenKeeper.readAccessToken(MyAccountActivity.this);
        if (mAccessToken.isSessionValid()) {
            showToast("logout");
            showProgressDialog();
            requestUnbindSinaJson();
        } else {
            showToast("login");
            // SSO 授权
            mSsoHandler = new SsoHandler(MyAccountActivity.this, mWeiboAuth);
            mSsoHandler.authorize(new AuthListener(MyAccountActivity.this));
        }
    }

    /**
     * 当 SSO 授权 Activity 退出时，该函数被调用。
     *
     * @see {@link Activity#onActivityResult}
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        NSLog.d("requestCode:" + requestCode);
        NSLog.d("resultCode:" + resultCode);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
            case FLAG_ACTIVITY_REQUEST_NICKNAME_MODIFY:
                String nickname = data.getStringExtra(NSBizConstant.KEY_NICKNAME);
                NSLog.d("nickname:" + nickname);
                mNickname.setText(nickname);
                break;
            case FLAG_ACTIVITY_REQUEST_SIGN_MODIFY:
                String sign = data.getStringExtra(NSBizConstant.KEY_SIGN);
                NSLog.d("sign:" + sign);
                mSignature.setText(sign);
                break;
            case FLAG_ACTIVITY_REQUEST_SINA_LOGIN:
                // SSO 授权回调
                // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResult
                if (mSsoHandler != null) {
                    mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
                }
                mAccessToken = AccessTokenKeeper.readAccessToken(MyAccountActivity.this);
                if (mAccessToken.isSessionValid()) {
                    showProgressDialog();
                    requestBindSinaJson();
                } else {
                    mWeiboBtn.setText(R.string.bind);
                }
                showToast(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US).format(
                        new java.util.Date(mAccessToken.getExpiresTime())));
                break;
            default:
                break;
            }
        } else {
            setResult(RESULT_CANCELED);
        }
    }

    public void setSex(View v) {
        NSLog.i(TAG, "setSex");

        final String[] items ={"女","男"};
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.alertdialog, null);
        ListView lv  = (ListView) view.findViewById(R.id.alertdialog_listview);
        lv.setAdapter(new ArrayAdapter<Object>(this, R.layout.alertdialog_item, items));
        lv.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                alertdialog.dismiss();
                showProgressDialog();
                requestSetSexJson(String.valueOf(position));
            }
        });
        alertdialog = new AlertDialog.Builder(this).create();
        // 在此使用setview方法可以设置布局文件和alertdialog四周边框的距离，可以消除黑边框
        alertdialog.setView(view, 0, 0, 0, 0);
        alertdialog.show();
    }

    private void requestSetSexJson(String sex) {
        NSLog.i(TAG, "requestSetSexJson");
        try {
            String msg = NSMsgFactory.reqSetSexMsg(userId, sex);
            postMessage(NSBizConstant.REQUEST_SET_SEX,
                    NSAppConfig.SERVER_URL + NSMsgFactory.CMD_SET_SEX, NSHttpManager.POST, msg);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPhone.setText(((NSApplication) getApplication()).getPhone());
    }


    private void requestBindSinaJson() {
        NSLog.i(TAG, "requestBindSinaJson");
        try {
            mAccessToken = AccessTokenKeeper.readAccessToken(MyAccountActivity.this);
            String msg = NSMsgFactory.reqBindSinaMsg(userId, mAccessToken.getUid(), mAccessToken.getToken(), String.valueOf(mAccessToken.getExpiresTime()));
            postMessage(NSBizConstant.REQUEST_BIND_SINA,
                    NSAppConfig.SERVER_URL + NSMsgFactory.CMD_BIND_SINA, NSHttpManager.POST, msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void requestUnbindSinaJson() {
        NSLog.i(TAG, "requestUnbindSinaJson");
        try {
            String msg = NSMsgFactory.reqBindSinaMsg(userId, "", "", "");
            postMessage(NSBizConstant.REQUEST_UNBIND_SINA,
                    NSAppConfig.SERVER_URL + NSMsgFactory.CMD_BIND_SINA, NSHttpManager.POST, msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
