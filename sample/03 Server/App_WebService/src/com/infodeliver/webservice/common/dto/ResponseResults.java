package com.infodeliver.webservice.common.dto;

import java.util.HashMap;
import java.util.Map;

public class ResponseResults {

    private String v;
    private String cmd;
    private String subDS;
    private String resp;// error
    private String msg;// error msg
    private Map<String, Object> params;

    public ResponseResults (RequestParameters params) {
        setV(params.getV());
        setCmd(params.getCmd());
        setSubDS(params.getSubDS());
        setResp(params.getResp());
        setMsg(params.getMsg());
        this.params = new HashMap<>();
    }

    public String getV() {
        return v;
    }
    public void setV(String v) {
        this.v = v;
    }
    public String getCmd() {
        return cmd;
    }
    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getSubDS() {
        return subDS;
    }
    public void setSubDS(String subDS) {
        this.subDS = subDS;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public void putResult(String key, Object value){
        params.put(key, value);
    }

    public void putResultAll(Map<String, Object> result){
        params.putAll(result);
    }

	public String getResp() {
		return resp;
	}

	public void setResp(String resp) {
		this.resp = resp;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
    
}
