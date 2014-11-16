package com.infodeliver.webservice.captcha.service;

import org.dom4j.Element;

public interface ICaptchaService {

    public Element sendSMSCode(String phone) throws Exception;

    public int sendSMSCodeTest(String phone);

    public boolean checkPhone(String phone);
}
