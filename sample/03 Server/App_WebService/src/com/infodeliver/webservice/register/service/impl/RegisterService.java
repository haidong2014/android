package com.infodeliver.webservice.register.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.infodeliver.webservice.common.service.AbstractBaseService;
import com.infodeliver.webservice.common.util.DateUtil;
import com.infodeliver.webservice.login.dao.ILoginDao;
import com.infodeliver.webservice.register.dao.IRegisterDao;
import com.infodeliver.webservice.register.service.IRegisterService;

public class RegisterService extends AbstractBaseService implements IRegisterService {

    @Autowired
    private IRegisterDao dao;

    @Autowired
    private ILoginDao loginDao;

    @Override
    @Transactional(propagation = Propagation.NEVER, readOnly=true)
    public int checkSmsCode(String phone, String smsCode) {
        Map<String, Object> r = dao.getLastCaptcha(phone);
        if (r.isEmpty()) {
            return 1;
        } else {
            Date cd = (Date) r.get("CREATE_DATE");
            long l = DateUtil.getSystemDate().getTime() - cd.getTime();
            long day = l / (24 * 60 * 60 * 1000);
            long hour = l / (60 * 60 * 1000) - day * 24;
            long min = (l / (60 * 1000)) - day * 24 * 60 - hour * 60;
            if (min > 10){
                return 2;
            } else if (!smsCode.equals(r.get("CAPTCHA").toString()))  {
                return 3;
            }
        }
        return 0;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Map<String, Object> register(String phone, String password, String nickname) {
        int insert = dao.register(phone, password, nickname);
        if(insert == 1) {
            Map<String, Object> r = loginDao.getUserByTel(phone,password);
            r.put("register_flag", "1");
            r.put("msg", "注册成功");
            return r;
        } else {
            Map<String, Object> r = new HashMap<>();
            r.put("register_flag", "0");
            r.put("msg", "注册失败");
            return r;
        }
    }
}
