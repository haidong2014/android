package com.infodeliver.client.base;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.infodeliver.io.NSHttpTask;
import com.infodeliver.io.NSTaskCallback;
import com.infodeliver.utils.NSErrors;
import com.infodeliver.utils.NSLog;
import com.infodeliver.utils.NSResourceManager;

/**
 * Base.
 *
 * @author yujian
 * @version 1.0
 * @createDate 2014-06-23
 * @lastUpdate 2014-06-23
 */
public abstract class NSActivityBase extends Activity implements NSTaskCallback {

    private static final String TAG = "NSActivityBase";

    protected ArrayList<NSHttpTask> mHttpTasks = new ArrayList<NSHttpTask>();
    private Toast mToast;

    private View mLoadingView;
    private View mContentView;
    private View mLoadFailView;
    private View mReloadBtn;
    private View mLoadingContainer;
    private TextView mLoadFailTip;

    /**
     * postMessage
     *
     * @param requestID
     * @param server : URL
     * @param method : NSHttpManager.POST or NSHttpManager.GET
     * @param msg : JSONObject.toString()
     */
    public void postMessage(int requestID, String server, String method,
            String msg) {

        NSLog.d(TAG, "requestID:" + requestID);
        NSLog.d(TAG, "server:" + server);
        NSLog.d(TAG, "method:" + method);
        NSLog.d(TAG, "msg:" + msg);
        NSHttpTask task = new NSHttpTask(requestID, this);
        task.execute(server, method, msg);
        mHttpTasks.add(task);
    }

    @Override
    public void onResult(int requestID, String result) {
    }

    @Override
    public void onError(int requestID, String errorCode, String errorDesc) {
        if (!isFinishing()) {
            if (null == errorDesc || 0 == errorDesc.length()) {
                errorDesc = NSErrors.getLocalErrorMsg(this, errorCode);
            }
            showToast(errorDesc);
        }

        // If the error is login timeout or kick out, jump to login activity.
        if (NSErrors.ERROR_LOGIN_TIMEOUT.equals(errorCode)
                || NSErrors.ERROR_KICK_OUT.equals(errorCode)) {
        }
    }

    public void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                cancelToast();
                mToast = Toast.makeText(NSActivityBase.this, msg,
                        Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }

    public void cancelToast() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (null != mToast) {
                    mToast.cancel();
                }
            }
        });
    }

    protected void showContentView() {
        if (null == mLoadingView) {
            initLoadingView();
        }
        mLoadingContainer.setVisibility(View.INVISIBLE);
        mContentView.setVisibility(View.VISIBLE);
        mLoadFailView.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.GONE);
    }

    protected void initLoadingView() {
        mLoadingContainer = findViewById(NSResourceManager.getResourceID(
                "view_loading_container", "id"));
        mLoadingView = findViewById(NSResourceManager.getResourceID(
                "view_loading", "id"));
        mLoadFailView = findViewById(NSResourceManager.getResourceID(
                "view_loading_fail", "id"));
        mContentView = findViewById(NSResourceManager.getResourceID(
                "view_content_container", "id"));
        mReloadBtn = findViewById(NSResourceManager.getResourceID("btn_reload",
                "id"));
        mLoadFailTip = (TextView) findViewById(NSResourceManager.getResourceID(
                "tip_load_fail", "id"));
        mReloadBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showLoadingView();
                onReloadContent();
            }
        });
    }

    protected void showLoadingView() {
        if (null == mLoadingView) {
            initLoadingView();
        }
        mLoadingContainer.setVisibility(View.VISIBLE);
        mLoadingView.setVisibility(View.VISIBLE);
        mLoadFailView.setVisibility(View.GONE);
        mContentView.setVisibility(View.GONE);
    }

    protected void showLoadFailView() {
        showLoadFailView(null);
    }

    protected void showLoadFailView(CharSequence failTip) {
        if (null == mLoadingView) {
            initLoadingView();
        }
        if (null != failTip) {
            mLoadFailTip.setText(failTip);
        }
        mLoadingContainer.setVisibility(View.VISIBLE);
        mLoadFailView.setVisibility(View.VISIBLE);
        mLoadingView.setVisibility(View.GONE);
        mContentView.setVisibility(View.GONE);
    }

    protected void onReloadContent() {
    }

    protected void closeInput(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(NSActivityBase.this.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private ProgressDialog progressDialog;

    /*
     * 提示加载
     */
    public void showProgressDialog() {
        String message = "正在修改请稍后...";
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(message);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
        } else if (progressDialog.isShowing()) {
            progressDialog.setMessage(message);
        }

        progressDialog.show();
    }

    /*
     * 隐藏提示加载
     */
    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

}
