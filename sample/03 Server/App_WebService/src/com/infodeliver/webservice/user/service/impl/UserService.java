package com.infodeliver.webservice.user.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.infodeliver.webservice.common.service.AbstractBaseService;
import com.infodeliver.webservice.user.dao.IUserDao;
import com.infodeliver.webservice.user.service.IUserService;

public class UserService extends AbstractBaseService implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean setSex(String sex, String userId) {
        return userDao.updateForSex(sex, userId) > 0;
    }

    @Override
    @Transactional(propagation = Propagation.NEVER, readOnly=true)
    public Map<String, Object> getUser(String userId, String password) {
        return userDao.getUserById(userId, password);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean modifyNickname(String nickname, String userId) {
        return userDao.modifyNickname(nickname, userId) > 0;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean modifySign(String sign, String userId) {
        return userDao.modifySign(sign, userId) > 0;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int modifyPwd(String odlPwd, String password, String userId) {
         Map<String, Object> r = userDao.getUserById(userId, odlPwd);
         if (r.isEmpty()) {
             return -1;
         }
         return userDao.modifyPwd(password, userId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean modifyPhone(String phone, String userId) {
        return userDao.modifyPhone(phone, userId) > 0;
    }

    @Override
    @Transactional(propagation = Propagation.NEVER, readOnly=true)
    public boolean checkBindPhone(String phone, String userId) {
        Map<String, Object> r = userDao.getUserByPhone(userId, phone);
        return !r.isEmpty();
    }

    @Override
    public boolean bindSina(String sinaUid, String sinaToken,
            String sinaExpires, String userId) {
        return userDao.bindSina(sinaUid, sinaToken, sinaExpires, userId) > 0;
    }
}
