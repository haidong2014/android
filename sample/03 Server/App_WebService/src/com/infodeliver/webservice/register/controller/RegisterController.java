package com.infodeliver.webservice.register.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.infodeliver.webservice.common.controller.AbstractBaseController;
import com.infodeliver.webservice.common.dto.RequestParameters;
import com.infodeliver.webservice.common.dto.ResponseResults;
import com.infodeliver.webservice.register.service.IRegisterService;

@Controller
public class RegisterController extends AbstractBaseController {

    @Autowired
    private IRegisterService service;

    @RequestMapping(value = "/check_sms_code", method = RequestMethod.POST)
    public @ResponseBody ResponseResults checkSmsCode(@RequestBody RequestParameters params)
    {
        logger.debug("phone:"+params.getParameter("phone"));
        logger.debug("sms_code:"+params.getParameter("sms_code"));
        int checkFlag = service.checkSmsCode(params.getParameter("phone"), params.getParameter("sms_code"));

        ResponseResults results = new ResponseResults(params);
        results.putResult("check_flag", checkFlag);
        if (checkFlag == 1) {
            results.putResult("msg", "请先获取验证码");
        } else if (checkFlag == 2) {
            results.putResult("msg", "验证码已过期，请重新获取验证码");
        } else if (checkFlag == 3) {
            results.putResult("msg", "验证码错误");
        } else {
            results.putResult("msg", "成功");
        }
        return results;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public @ResponseBody ResponseResults register(@RequestBody RequestParameters params)
    {
        logger.debug("phone:"+params.getParameter("phone"));
        logger.debug("password:"+params.getParameter("password"));
        logger.debug("nickname:"+params.getParameter("nickname"));

        Map<String, Object> result = service.register(params.getParameter("phone"),
                params.getParameter("password"), params.getParameter("nickname"));

        ResponseResults results = new ResponseResults(params);
        results.setParams(result);
        return results;
    }
}
