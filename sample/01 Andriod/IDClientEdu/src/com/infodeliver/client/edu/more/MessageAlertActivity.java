package com.infodeliver.client.edu.more;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.infodeliver.client.base.NSActivityBase;
import com.infodeliver.client.edu.R;
import com.infodeliver.client.utils.NSBizConstant;
import com.infodeliver.utils.NSLog;
import com.infodeliver.utils.NSSharePreferenceKeeper;

public class MessageAlertActivity extends NSActivityBase {

    private static final String TAG = "MessageAlertActivity";

    private TextView mBackLabel;

    private ToggleButton mReceiveAlertBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_alert);
        initTextView();
    }

    /**
     * init title
     */
    private void initTextView() {
        mBackLabel = (TextView) findViewById(R.id.back_label);
        mBackLabel.setClickable(true);

        mReceiveAlertBtn = (ToggleButton) findViewById(R.id.receive_alert_btn);
        mReceiveAlertBtn.setChecked(NSSharePreferenceKeeper.getBooleanValue(MessageAlertActivity.this, NSBizConstant.SPK_RECEIVE_NEW_ALERT, true, false));
        mReceiveAlertBtn.setOnCheckedChangeListener(new OnCheckedChangeListener(){
            @Override
              public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                  if(isChecked) {
                      NSSharePreferenceKeeper.keepBooleanValue(MessageAlertActivity.this, NSBizConstant.SPK_RECEIVE_NEW_ALERT, true, false);
                      showToast("打开后，将接收到新消息的提醒。");
                  }else{
                      NSSharePreferenceKeeper.keepBooleanValue(MessageAlertActivity.this, NSBizConstant.SPK_RECEIVE_NEW_ALERT, false, false);
                      showToast("关闭后，将不再接收到新消息的提醒。");
                  }
              }
       });
    }

    @Override
    public void onError(int requestID, String errorCode, String errorDesc) {
        super.onError(requestID, errorCode, errorDesc);
        NSLog.i(TAG, "onError");
        NSLog.e(errorCode);
        showContentView();
    }

    public void doBack(View v) {
        NSLog.i(TAG, "doBack");
        finish();
    }
}
