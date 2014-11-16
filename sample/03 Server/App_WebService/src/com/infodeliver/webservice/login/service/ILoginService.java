package com.infodeliver.webservice.login.service;

import java.util.Map;

public interface ILoginService {
    public Map<String, Object> login(String phone, String pwd);

    public boolean passwordReset(String phone, String pwd);
}
