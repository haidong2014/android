package com.infodeliver.webservice.user.service;

import java.util.Map;

public interface IUserService {

    public boolean setSex(String sex, String userId);

    public boolean modifyNickname(String nickname, String userId);

    public boolean modifySign(String sign, String userId);

    public boolean modifyPhone(String phone, String userId);

    public boolean checkBindPhone(String phone, String userId);

    public boolean bindSina(String sinaUid, String sinaToken, String sinaExpires,String userId);

    public int modifyPwd(String odlPwd, String password, String userId);

    public Map<String, Object> getUser(String userId, String password);
}
