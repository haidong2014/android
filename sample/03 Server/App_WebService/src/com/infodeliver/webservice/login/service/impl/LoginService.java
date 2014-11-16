package com.infodeliver.webservice.login.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.infodeliver.webservice.common.service.AbstractBaseService;
import com.infodeliver.webservice.login.dao.ILoginDao;
import com.infodeliver.webservice.login.service.ILoginService;

public class LoginService extends AbstractBaseService implements ILoginService {

    @Autowired
    private ILoginDao loginDao;

    @Override
    @Transactional(propagation = Propagation.NEVER, readOnly=true)
    public Map<String, Object> login(String phone, String pwd){
        Map<String, Object> r = loginDao.getUserByTel(phone,pwd);
        if (!r.isEmpty()) {
            r.put("is_login", true);
        } else {
            r.put("is_login", false);
        }
        return r;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean passwordReset(String phone, String pwd) {
        if (loginDao.updatePwdByTel(phone,pwd) > 0){
            return true;
        }
        return false;
    }

}
