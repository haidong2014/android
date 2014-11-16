package com.infodeliver.webservice.register.service;

import java.util.Map;

public interface IRegisterService {

    public int checkSmsCode(String phone, String smsCode);

    public Map<String, Object> register(String phone, String password, String nickname);
}
