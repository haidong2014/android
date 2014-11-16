package com.infodeliver.client.base;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.infodeliver.client.utils.NSBizConstant;
import com.infodeliver.io.NSHttpTask;
import com.infodeliver.io.NSTaskCallback;
import com.infodeliver.utils.NSConstant;
import com.infodeliver.utils.NSErrors;
import com.infodeliver.utils.NSLog;

/**
 * The base service in application.
 * 
 * @author yujian
 * @version 1.0
 * @createDate 2014-8-5
 * @lastUpdate 2014-8-5
 */
public abstract class NSServiceBase extends Service implements NSTaskCallback {

	private static final String TAG = "NSServiceBase";
    private Toast mToast;
    protected ArrayList<NSHttpTask> mHttpTasks = new ArrayList<NSHttpTask>();
    
    @Override
    public void onCreate() {
    	NSLog.i("NSServiceBase>>>onCreate");
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

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
    
    protected void onError(int requestID, String errorCode) {
        String errorDesc = NSErrors.getLocalErrorMsg(this, errorCode);
        onError(requestID, errorCode, errorDesc);
    }

    protected void onDownloadProgress(int requestID, int progress) {

    }

    protected JSONObject getParamsFromResp(int requestID, String result)
            throws JSONException {
        JSONObject json = new JSONObject(result);
        String respCode = json.getString(NSConstant.KEY_RESP_CODE);
        NSLog.d("respCode(biz):" + respCode);
        if (NSErrors.SUCCESS.equals(respCode)) {
            return json.getJSONObject(NSBizConstant.KEY_PARAMS);
        } else {
            String respDesc = json.getString(NSConstant.KEY_RESP_DESC);
            onError(requestID, respCode, respDesc);
            return null;
        }
    }

    protected void showToast(String msg) {
        mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        mToast.show();
    }

    protected void cancelToast() {
        if (null != mToast) {
            mToast.cancel();
        }
    }
}
