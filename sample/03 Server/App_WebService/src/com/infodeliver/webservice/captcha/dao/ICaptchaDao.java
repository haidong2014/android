package com.infodeliver.webservice.captcha.dao;

import java.util.Map;

public interface ICaptchaDao {

    public void insert(String phone, int captcha);

    public Map<String, Object> getPhone(String phone);
}
