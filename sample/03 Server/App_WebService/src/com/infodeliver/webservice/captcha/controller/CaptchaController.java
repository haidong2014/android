package com.infodeliver.webservice.captcha.controller;

import java.text.MessageFormat;

import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.infodeliver.webservice.captcha.service.ICaptchaService;
import com.infodeliver.webservice.common.controller.AbstractBaseController;
import com.infodeliver.webservice.common.dto.RequestParameters;
import com.infodeliver.webservice.common.dto.ResponseResults;

@Controller
public class CaptchaController extends AbstractBaseController {

    @Autowired
    private ICaptchaService service;

    @RequestMapping(value = "/send_sms_code", method = RequestMethod.POST)
    public @ResponseBody ResponseResults sendSmsCode(@RequestBody RequestParameters params)
    {
        String phone = params.getParameter("phone");
        String forgetFlag = params.getParameter("forget_flag");

        logger.debug("phone:"+phone);
        logger.debug("forget_flag:"+forgetFlag);
        if ("1".equals(forgetFlag)) {
            if (!service.checkPhone(phone)) {
                logger.debug("********************code" + "100099");
                logger.debug("********************msg" + "用户手机号未注册");
                ResponseResults results = new ResponseResults(params);
                results.putResult("service_exec_mode", serviceExecMode());
                results.putResult("code", "100099");
                results.putResult("msg", "用户手机号未注册");
                return results;
            }
        } else if ("0".equals(forgetFlag)) {
            if (service.checkPhone(phone)) {
                logger.debug("********************code" + "100099");
                logger.debug("********************msg" + "用户手机号已注册");
                ResponseResults results = new ResponseResults(params);
                results.putResult("service_exec_mode", serviceExecMode());
                results.putResult("code", "100098");
                results.putResult("msg", "用户手机号已注册");
                return results;
            }
        }
        try {
            if ("TEST".equals(serviceExecMode())) {
                int captcha = service.sendSMSCodeTest(phone);
                ResponseResults results = new ResponseResults(params);
                results.putResult("service_exec_mode", serviceExecMode());
                results.putResult("code", "2");
                results.putResult("message", MessageFormat.format("您的验证码是：{0}。请不要把验证码泄露给其他人。", String.valueOf(captcha)));
                return results;
            }
            Element sendResult = service.sendSMSCode(phone);

            String code = sendResult.elementText("code");
            String msg = sendResult.elementText("msg");
            logger.debug("********************code" + code);
            logger.debug("********************msg" + msg);

            ResponseResults results = new ResponseResults(params);
            results.putResult("service_exec_mode", serviceExecMode());
            results.putResult("code", code);
            if (!code.equals("2")) {
                results.putResult("msg", "不能获取验证码，请稍后再试");
            }
            return results;
        } catch (Exception e) {
            ResponseResults results = new ResponseResults(params);
            results.putResult("service_exec_mode", serviceExecMode());
            results.putResult("code", "999999");
            results.putResult("msg", "不能获取验证码，请稍后再试");
            return results;
        }
    }
}
