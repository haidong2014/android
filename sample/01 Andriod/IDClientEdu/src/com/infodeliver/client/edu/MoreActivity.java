/**
 *
 */
package com.infodeliver.client.edu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infodeliver.client.base.NSActivityBase;
import com.infodeliver.client.edu.login.LoginActivity;
import com.infodeliver.client.edu.more.MessageAlertActivity;
import com.infodeliver.client.edu.more.MyAccountActivity;
import com.infodeliver.client.utils.NSBizConstant;
import com.infodeliver.utils.NSLog;
import com.infodeliver.utils.NSSharePreferenceKeeper;

public class MoreActivity extends NSActivityBase {
    private static final String TAG = "MoreActivity";

    private static final int FLAG_ACTIVITY_REQUEST_MY_ACCOUNT = 1;

    private LinearLayout mUserSet;
    private TextView mUserName;
    private LinearLayout mNoticeSet;
    private LinearLayout mRecommendFriend;
    private LinearLayout mFeedback;
    private LinearLayout mAboutUs;
    private LinearLayout mClearCache;

    private ImageView mUserImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_tab);
        initTextView();
    }

    /**
     * init title
     */
    private void initTextView() {
        mUserSet = (LinearLayout) findViewById(R.id.user_set);
        mUserSet.setClickable(true);

        mUserName = (TextView) findViewById(R.id.user_name);
        mUserName.setText(NSSharePreferenceKeeper.getStringValue(this,  NSBizConstant.SPK_USER_NAME, false));

        mNoticeSet = (LinearLayout) findViewById(R.id.notice_set);
        mNoticeSet.setClickable(true);

        mRecommendFriend = (LinearLayout) findViewById(R.id.recommend_friend);
        mRecommendFriend.setClickable(true);

        mFeedback = (LinearLayout) findViewById(R.id.feedback);
        mFeedback.setClickable(true);

        mAboutUs = (LinearLayout) findViewById(R.id.about_us);
        mAboutUs.setClickable(true);

        mClearCache = (LinearLayout) findViewById(R.id.clear_cache);
        mClearCache.setClickable(true);

        mUserImg = (ImageView) findViewById(R.id.user_img);
//        mUserImg.set
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
        showContentView();
    }

    @Override
    protected void onReloadContent() {
        NSLog.i(TAG, "onReloadContent");
        super.onReloadContent();
    }

    public void doUserSet(View v) {
        NSLog.i(TAG, "doUserSet");
        Intent intent = new Intent(this,MyAccountActivity.class);
        startActivityForResult(intent, FLAG_ACTIVITY_REQUEST_MY_ACCOUNT);
    }

    public void doNoticeSet(View v) {
        NSLog.i(TAG, "doNoticeSet");
        Intent intent = new Intent(this,MessageAlertActivity.class);
        startActivity(intent);
    }

    public void doRecommendFriend(View v) {
        NSLog.i(TAG, "doRecommendFriend");
        showToast("doRecommendFriend");
    }

    public void doFeedback(View v) {
        NSLog.i(TAG, "doFeedback");
        showToast("doFeedback");
    }

    public void doAboutUs(View v) {
        NSLog.i(TAG, "doAboutUs");
        showToast("doAboutUs");
    }

    public void doClearCache(View v) {
        NSLog.i(TAG, "doClearCache");
        AlertDialog alert = new AlertDialog.Builder(this).create();
        alert.setTitle("系统提示");
        alert.setMessage("是否清空缓存？");
        alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                NSSharePreferenceKeeper.clearPersonal(MoreActivity.this);
                showToast("缓存已被清空。");
            }
        });

        alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.show();

    }

    public void doLogout(View v) {
        NSLog.i(TAG, "doLogout");
        NSSharePreferenceKeeper.removeKey(this, NSBizConstant.SPK_ALREADY_LOGIN, false);
        NSSharePreferenceKeeper.removeKey(this, NSBizConstant.SPK_USER_ID, false);
        NSSharePreferenceKeeper.removeKey(this,  NSBizConstant.SPK_USER_NAME, false);
        NSSharePreferenceKeeper.removeKey(this, NSBizConstant.SPK_USER_SEX, false);
        NSSharePreferenceKeeper.removeKey(this, NSBizConstant.SPK_USER_SIGN, false);
        NSSharePreferenceKeeper.removeKey(this, NSBizConstant.SPK_USER_TEL, false);

        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        NSLog.d("requestCode:" + requestCode);
        NSLog.d("resultCode:" + resultCode);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
            case FLAG_ACTIVITY_REQUEST_MY_ACCOUNT:
                Intent backIntent = data;
                String nickname = backIntent.getStringExtra(NSBizConstant.KEY_NICKNAME);
                NSLog.d("nickname:" + nickname);
                mUserName.setText(nickname);
                break;
            default:
                break;
            }
        } else {
            setResult(RESULT_CANCELED);
        }
    }
}
