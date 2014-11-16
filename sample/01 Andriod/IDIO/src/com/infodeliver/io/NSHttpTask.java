package com.infodeliver.io;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import android.os.AsyncTask;
import android.os.Bundle;

import com.infodeliver.utils.NSConstant;
import com.infodeliver.utils.NSErrors;
import com.infodeliver.utils.NSLog;

/**
 * Task to send HTTP request. 5 params should be set when
 * {@link #execute(String...)} is called. The first parameter is the URL of the
 * server, the second parameter is {@link NSHttpManager#POST} or
 * {@link NSHttpManager#GET}, the third parameter is "true" or "false" to
 * indicate whether we need to encrypt the message or not, the fourth parameter
 * is the content of the request message, and the last parameter means ID of the
 * session.
 * 
 * @author yujian
 * @version 1.0
 * @createDate 2014-06-23
 * @lastUpdate 2014-06-23
 */
public class NSHttpTask extends AsyncTask<String, Integer, Bundle> {
    public static final int TASK_PAY = 1;
    public static final int TASK_NORMAL = 2;

    private static HashMap<String, String> sHeaderNormal = new HashMap<String, String>();

    static {

         sHeaderNormal.put(NSConstant.KEY_LOCALE, "zh-CN");
         sHeaderNormal.put(NSConstant.KEY_ZIP, "gzip");
    }

    private int mRequestID;
    private NSTaskCallback mCallback;

    public NSHttpTask(int requestID, NSTaskCallback callback) {
        mRequestID = requestID;
        mCallback = callback;
    }

    @Override
    protected Bundle doInBackground(String... params) {
        Bundle result = new Bundle();
        result.putString(NSConstant.KEY_RESP_CODE, NSErrors.SUCCESS);
        String serverUrl = params[0];
        String method = params[1];
        String msg = params[2];

        HashMap<String, String> headers = null;
        headers = new HashMap<String, String>();
        Set<String> keys = sHeaderNormal.keySet();
        for (String key : keys) {
            headers.put(key, sHeaderNormal.get(key));
        }

        try {
            String resp = null;
            
            resp = NSHttpManager.sendMessage(serverUrl, msg, method, headers);
            if (null != resp) {
                result.putString(NSConstant.KEY_RESP_DESC, resp);
            } else {
                result.putString(NSConstant.KEY_RESP_CODE,
                        NSErrors.ERROR_NETWORK);
            }
        } catch (IOException e) {
            e.printStackTrace();
            result.putString(NSConstant.KEY_RESP_CODE, NSErrors.ERROR_NETWORK);
        }
        return result;
    }

    @Override
    protected void onPostExecute(Bundle result) {
        String respCode = result.getString(NSConstant.KEY_RESP_CODE);
        String respMsg = result.getString(NSConstant.KEY_RESP_DESC);
        NSLog.i("respCode:" + respCode);
        if (NSErrors.SUCCESS.equals(respCode)) {
            onResult(respMsg);
        } else {
            onError(respCode, respMsg);
        }
    }

    protected void onResult(String respMsg) {
        mCallback.onResult(mRequestID, respMsg);
    }

    protected void onError(String respCode, String respMsg) {
        mCallback.onError(mRequestID, respCode, respMsg);
    }
}
