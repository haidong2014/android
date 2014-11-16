package com.infodeliver.webservice.register.dao;

import java.util.Map;

public interface IRegisterDao {

    public Map<String, Object> getLastCaptcha(String phone);

    public int register(String phone, String password, String nickname);
}
