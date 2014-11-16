package com.infodeliver.webservice.captcha.service.impl;

import java.util.Map;

import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.infodeliver.webservice.captcha.dao.ICaptchaDao;
import com.infodeliver.webservice.captcha.service.ICaptchaService;
import com.infodeliver.webservice.common.service.AbstractBaseService;
import com.infodeliver.webservice.common.util.SmsUtil;

public class CaptchaService extends AbstractBaseService implements ICaptchaService {

    @Autowired
    private ICaptchaDao dao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Element sendSMSCode(String phone) throws Exception {
        int captcha = (int)((Math.random()*9+1)*1000);
        Element sendResult = SmsUtil.send(phone, captcha);

        String code = sendResult.elementText("code");
        if(code == "2"){
            logger.debug("********************短信提交成功");
            dao.insert(phone, captcha);
        }

        return sendResult;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int sendSMSCodeTest(String phone) {
        int captcha = (int)((Math.random()*9+1)*1000);
        logger.debug("测试用验证码提交成功");
        dao.insert(phone, captcha);
        return captcha;
    }

    @Override
    @Transactional(propagation = Propagation.NEVER, readOnly=true)
    public boolean checkPhone(String phone) {
        Map<String, Object> r = dao.getPhone(phone);
        if (Integer.parseInt(r.get("CNT").toString()) > 0) {
            return true;
        }
        return false;
    }
}
