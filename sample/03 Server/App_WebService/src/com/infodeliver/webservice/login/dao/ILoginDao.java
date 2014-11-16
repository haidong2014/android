package com.infodeliver.webservice.login.dao;

import java.util.Map;

public interface ILoginDao {
    public Map<String, Object> getUserByTel(String phone, String pwd);

    public int updatePwdByTel(String phone, String pwd);
}
