package com.infodeliver.webservice.common.dto;

import java.util.Map;

public class RequestParameters {

    private String v;
    private String cmd;
    private String subDS;
    private String resp;// error
    private String msg;// error msg
    private Map<String, String> params;

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
    public Map<String, String> getParams() {
        return params;
    }
    public void setParams(Map<String, String> params) {
        this.params = params;
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
	public String getParameter(String key){
        if (params == null) {
            return "";
        } else if (params.containsKey(key)) {
            return params.get(key);
        } else {
            return "";
        }
    }
}
