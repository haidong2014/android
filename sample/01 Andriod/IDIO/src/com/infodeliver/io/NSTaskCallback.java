package com.infodeliver.io;

/**
 * Callback for AsyncTask.
 * 
 * @author yujian
 * @version 1.0
 * @createDate 2014-06-23
 * @lastUpdate 2014-06-23
 */
public interface NSTaskCallback {
    /**
     * This function will be called when error happened during task execution.
     * 
     * @param requestID ID of the request.
     * @param errorCode Error code.
     * @param errorDesc Error description, may be empty string.
     */
    public void onError(int requestID, String errorCode, String errorDesc);

    /**
     * This function will be called when task has finished.
     * 
     * @param requestID ID of the request.
     * @param result result of the task, for {@link NSHttpTask}, this is the
     *            response from server.
     */
    public void onResult(int requestID, String result);
}
