package com.infodeliver.io;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.infodeliver.utils.NSLog;

/**
 * Utils for http connection.
 * 
 * @author yujian
 * @version 1.0
 * @createDate 2014-06-23
 * @lastUpdate 2014-06-23
 */
public class NSHttpManager {
    public static final String POST = "POST";
    public static final String GET = "GET";

    private static final int HTTP_TIME_OUT = 40000;

    private static DefaultHttpClient mClient;

    static {
        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, HTTP_TIME_OUT);
        HttpConnectionParams.setSoTimeout(params, HTTP_TIME_OUT);

        mClient = new DefaultHttpClient(params);
    }

    /**
     * Send a HTTP message. <b>This should not be called in main thread.</b>
     * 
     * @param server URL of target server
     * @param message message content
     * @param method {@link #POST} or {@link #GET}
     * @return response from server
     * @throws IOException
     */
    static final String sendMessage(String server, String message,
            String method, HashMap<String, String> header) throws IOException {
        NSLog.d("sendMessage:" + server + ", " + method + ", " + message);
        HttpResponse response = null;

        StringEntity entity = null;
        if (!TextUtils.isEmpty(message)) {
            entity = new StringEntity(message, "UTF-8");
        }

        //for server spring auto bean
        entity.setContentType("application/json");
        
        HttpUriRequest request = null;
        if (POST.equalsIgnoreCase(method)) {
            HttpPost post = new HttpPost(server);
            if (null != entity) {
                post.setEntity(entity);
            }
            request = post;
        } else {
            if (!TextUtils.isEmpty(message)) {
                JSONObject sendParam = null;
                try {
                    sendParam = new JSONObject(message);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                    return "";
                }
                @SuppressWarnings("unchecked")
				Iterator<String> it = sendParam.keys();
                String key = null;
                String temp = "?";
                while (it.hasNext()) {
                    key = it.next();
                    try {
                        temp += key + "=" + sendParam.getString(key) + "&";
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                request = new HttpGet(server + temp);
            } else {
                request = new HttpGet(server);
            }

        }

        if (null != header) {
            Iterator<String> it = header.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                request.addHeader(key, header.get(key));
            }
        }
        response = mClient.execute(request);
        NSLog.i("HttpStatus:",response.getStatusLine().getStatusCode() + "");
        if (null != response
                && HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
            String resp = EntityUtils.toString(response.getEntity(), "UTF-8");
            NSLog.d("response:" + resp);
            return resp;
        } else {
            throw new IOException();
        }
    }
}
