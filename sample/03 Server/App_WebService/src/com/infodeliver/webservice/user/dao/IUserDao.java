package com.infodeliver.webservice.user.dao;

import java.util.Map;

public interface IUserDao {

    public int updateForSex(String sex, String userId);

    public int modifyNickname(String nickname, String userId);

    public int modifySign(String sign, String userId);

    public int modifyPwd(String password, String userId);

    public int modifyPhone(String phone, String userId);

    public int bindSina(String sinaUid, String sinaToken, String sinaExpires,String userId);

    public Map<String, Object> getUserById(String userId, String password);

    public Map<String, Object> getUserByPhone(String userId, String phone);
}
