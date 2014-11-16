package com.infodeliver.client.edu.more;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.infodeliver.utils.NSUtils;

public class BindNewPhoneActivity extends NSActivityBase {

    private static final String TAG = "BindNewPhoneActivity";

    private TextView mBackLabel;

    private EditText mPhoneTxt;

    private EditText mSmsCodeTxt;

    private Button mSmsCodeButton;

    private BroadcastReceiver smsReceiver;
    private IntentFilter filter2;

    private String userId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        NSLog.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_new_phone);
        initTextView();
        NSUtils.finishActivity();
    }

    /**
     * init title
     */
    private void initTextView() {
        userId = NSSharePreferenceKeeper.getStringValue(this, NSBizConstant.SPK_USER_ID, false);
        mBackLabel = (TextView) findViewById(R.id.back_label);
        mBackLabel.setClickable(true);

        mSmsCodeButton = (Button) findViewById(R.id.sms_code_btn);
        mPhoneTxt = (EditText) findViewById(R.id.new_phone_text);
        mSmsCodeTxt = (EditText) findViewById(R.id.sms_code_text);
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
        case NSBizConstant.REQUEST_SEND_SMS_CODE:
            try {
                JSONObject json = new JSONObject(result);
                JSONObject rParams = (JSONObject) json
                        .get(NSBizConstant.KEY_PARAMS);
                // TODO 测试环境
                if ("TEST".equals(rParams.getString("service_exec_mode"))) {
                    if ("2".equals(rParams.getString("code"))) {
                        String message = rParams.getString("message");
                        String code = NSUtils.patternCode(message);
                        Message msg = mUIHandler.obtainMessage(2);
                        msg.obj = code;
                        msg.sendToTarget();
                    } else {
                        showToast(rParams.getString("msg"));
                    }
                } else {
                    if ("2".equals(rParams.getString("code"))) {
                        filter2 = new IntentFilter();
                        filter2.addAction("android.provider.Telephony.SMS_RECEIVED");
                        filter2.setPriority(Integer.MAX_VALUE);
                        smsReceiver = new BroadcastReceiver() {
                            @Override
                            public void onReceive(Context context, Intent intent) {
                                Object[] objs = (Object[]) intent.getExtras().get(
                                        "pdus");
                                for (Object obj : objs) {
                                    byte[] pdu = (byte[]) obj;
                                    SmsMessage sms = SmsMessage.createFromPdu(pdu);
                                    // 短信的内容
                                    String message = sms.getMessageBody();
                                    NSLog.d("logo", "message     " + message);
                                    // 短息的手机号。。+86开头？
                                    String from = sms.getOriginatingAddress();
                                    NSLog.d("logo", "from     " + from);
                                    if (!TextUtils.isEmpty(from)) {
                                        String code = NSUtils.patternCode(message);
                                        if (!TextUtils.isEmpty(code)) {
                                            Message msg = mUIHandler.obtainMessage(2);
                                            msg.obj = code;
                                            msg.sendToTarget();
                                        }
                                    }
                                }
                            }
                        };
                        registerReceiver(smsReceiver, filter2);
                    } else {
                        showToast(rParams.getString("msg"));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            break;
        case NSBizConstant.REQUEST_BIND_NEW_PHONE:
            hideProgressDialog();
            try {
                JSONObject json = new JSONObject(result);
                JSONObject rParams = (JSONObject) json.get(NSBizConstant.KEY_PARAMS);
                if (rParams.getBoolean("upd_flag")) {
                    String phone = rParams.getString("phone");
                    phone = phone.substring(0,3) + "****" + phone.substring(7);
                    ((NSApplication) getApplication()).setPhone(phone);
                    NSSharePreferenceKeeper.keepStringValue(this, NSBizConstant.SPK_USER_TEL, rParams.getString("phone"), false);
                    finish();
                } else if (rParams.getInt("check_flag") != 0) {
                    showToast(rParams.getString("msg"));
                } else {
                    showToast("绑定新手机号失败，请稍后再试");
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
        if (smsReceiver != null) {
            unregisterReceiver(smsReceiver);
        }
    }

    public void doBack(View v) {
        NSLog.i(TAG, "doBack");
        finish();
    }

    public void doSendSMSCode(View v) {
        NSLog.i(TAG, "doSendSMSCode");
        reqSendSMSCodeJson();
    }

    public void doBind(View v) {
        NSLog.i(TAG, "doBind");
        if (NSUtils.isFastDoubleClick()) {
            return;
        }
        closeInput();
        String phone = mPhoneTxt.getText().toString();
        String smsCode = mSmsCodeTxt.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            showToast("请输入新手机号。");
            return;
        } else if (TextUtils.isEmpty(smsCode)) {
            showToast("请输入手机收到的验证码。");
            return;
        }
        showProgressDialog();
        reqBindNewPhoneJson(phone, smsCode);
    }

    private void reqBindNewPhoneJson(String phone, String smsCode) {
        NSLog.i(TAG, "reqBindNewPhoneJson");
        try {

            String msg = NSMsgFactory.reqBindNewPhoneMsg(userId, phone, smsCode);
            postMessage(NSBizConstant.REQUEST_BIND_NEW_PHONE,
                    NSAppConfig.SERVER_URL + NSMsgFactory.CMD_BIND_NEW_PHONE,
                    NSHttpManager.POST, msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void countdown() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 60;
                while (i > 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i--;
                    if (i > 0) {
                        Message msg = mUIHandler.obtainMessage(1);
                        Bundle data = new Bundle();
                        data.putString("btn_text", i + "秒重新获取");
                        data.putBoolean("btn_enabled", false);
                        msg.obj = data;
                        msg.sendToTarget();
                    }
                }
                Message msg = mUIHandler.obtainMessage(1);
                Bundle data = new Bundle();
                data.putString("btn_text", "获取验证码");
                data.putBoolean("btn_enabled", true);
                msg.obj = data;
                msg.sendToTarget();
            }
        }).start();
    }

    private Handler mUIHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: {
                    Bundle data = (Bundle) msg.obj;
                    mSmsCodeButton.setText(data.getString("btn_text"));
                    mSmsCodeButton.setEnabled(data.getBoolean("btn_enabled"));
                    break;
                }
                case 2: {
                    String code = (String) msg.obj;
                    mSmsCodeTxt.setText(code);
                    break;
                }
            }
        }
    };

    private void reqSendSMSCodeJson() {
        NSLog.i(TAG, "reqSendSMSCodeJson");
        try {
            // demo
            String phone = mPhoneTxt.getText().toString();
            String msg = NSMsgFactory.reqSendSMSCodeMsg(phone, 0);
            postMessage(NSBizConstant.REQUEST_SEND_SMS_CODE,
                    NSAppConfig.SERVER_URL + NSMsgFactory.CMD_SEND_SMS_CODE , NSHttpManager.POST,
                    msg);
            countdown();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
