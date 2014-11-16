package com.infodeliver.webservice.user.controller;

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
import com.infodeliver.webservice.user.service.IUserService;

@Controller
public class UserController extends AbstractBaseController {

    @Autowired
    private IUserService service;

    @Autowired
    private IRegisterService registerService;

    @RequestMapping(value = "/set_sex", method = RequestMethod.POST)
    public @ResponseBody ResponseResults setSex(@RequestBody RequestParameters params)
    {
        logger.debug("user_id:"+params.getParameter("user_id"));
        logger.debug("sex:"+params.getParameter("sex"));

        boolean result = service.setSex(params.getParameter("sex"),params.getParameter("user_id"));
        ResponseResults results = new ResponseResults(params);
        results.putResult("sex", params.getParameter("sex"));
        results.putResult("upd_flag", result);
        return results;
    }

    @RequestMapping(value = "/get_user", method = RequestMethod.POST)
    public @ResponseBody ResponseResults getUser(@RequestBody RequestParameters params)
    {
        logger.debug("user_id:"+params.getParameter("user_id"));
        logger.debug("password:"+params.getParameter("password"));

        Map<String, Object> user = service.getUser(params.getParameter("user_id"), params.getParameter("password"));
        ResponseResults results = new ResponseResults(params);
        results.setParams(user);
        return results;
    }

    @RequestMapping(value = "/modify_nickname", method = RequestMethod.POST)
    public @ResponseBody ResponseResults modifyNickname(@RequestBody RequestParameters params)
    {
        logger.debug("user_id:"+params.getParameter("user_id"));
        logger.debug("nickname:"+params.getParameter("nickname"));

        boolean result = service.modifyNickname(params.getParameter("nickname"),params.getParameter("user_id"));
        ResponseResults results = new ResponseResults(params);
        results.putResult("nickname", params.getParameter("nickname"));
        results.putResult("upd_flag", result);
        return results;
    }

    @RequestMapping(value = "/modify_sign", method = RequestMethod.POST)
    public @ResponseBody ResponseResults modifySign(@RequestBody RequestParameters params)
    {
        logger.debug("user_id:"+params.getParameter("user_id"));
        logger.debug("sign:"+params.getParameter("sign"));

        boolean result = service.modifySign(params.getParameter("sign"),params.getParameter("user_id"));
        ResponseResults results = new ResponseResults(params);
        results.putResult("sign", params.getParameter("sign"));
        results.putResult("upd_flag", result);
        return results;
    }

    @RequestMapping(value = "/modify_pwd", method = RequestMethod.POST)
    public @ResponseBody ResponseResults modifyPwd(@RequestBody RequestParameters params)
    {
        logger.debug("user_id:"+params.getParameter("user_id"));
        logger.debug("old_password:"+params.getParameter("old_password"));
        logger.debug("password:"+params.getParameter("password"));

        int result = service.modifyPwd(params.getParameter("old_password"),params.getParameter("password"),params.getParameter("user_id"));
        ResponseResults results = new ResponseResults(params);
        results.putResult("password", params.getParameter("password"));
        if (result > 0) {
            results.putResult("upd_flag", true);
        } else {
            results.putResult("upd_flag", false);
            if (result < 0) {
                results.putResult("old_pwd_err", true);
            }
        }

        return results;
    }

    @RequestMapping(value = "/bind_new_phone", method = RequestMethod.POST)
    public @ResponseBody ResponseResults bindNewPhone(@RequestBody RequestParameters params)
    {
        logger.debug("user_id:"+params.getParameter("user_id"));
        logger.debug("phone:"+params.getParameter("phone"));
        logger.debug("sms_code:"+params.getParameter("sms_code"));

        int checkFlag = registerService.checkSmsCode(params.getParameter("phone"), params.getParameter("sms_code"));

        ResponseResults results = new ResponseResults(params);
        results.putResult("check_flag", checkFlag);
        if (checkFlag == 1) {
            results.putResult("msg", "请先获取验证码");
            results.putResult("upd_flag", false);
        } else if (checkFlag == 2) {
            results.putResult("msg", "验证码已过期，请重新获取验证码");
            results.putResult("upd_flag", false);
        } else if (checkFlag == 3) {
            results.putResult("msg", "验证码错误");
            results.putResult("upd_flag", false);
        } else {
            boolean result = service.modifyPhone(params.getParameter("phone"),params.getParameter("user_id"));
            results.putResult("phone", params.getParameter("phone"));
            results.putResult("upd_flag", result);
        }

        return results;
    }

    @RequestMapping(value = "/check_bind_phone", method = RequestMethod.POST)
    public @ResponseBody ResponseResults checkBindPhone(@RequestBody RequestParameters params)
    {
        logger.debug("user_id:"+params.getParameter("user_id"));
        logger.debug("phone:"+params.getParameter("phone"));

        boolean result = service.checkBindPhone(params.getParameter("phone"),params.getParameter("user_id"));
        ResponseResults results = new ResponseResults(params);
        results.putResult("check_flag", result);
        return results;
    }

    @RequestMapping(value = "/bind_sina", method = RequestMethod.POST)
    public @ResponseBody ResponseResults bindPhone(@RequestBody RequestParameters params)
    {
        logger.debug("user_id:"+params.getParameter("user_id"));
        logger.debug("sina_uid:"+params.getParameter("sina_uid"));
        logger.debug("sina_access_token:"+params.getParameter("sina_access_token"));
        logger.debug("sina_expires_in:"+params.getParameter("sina_expires_in"));

        boolean result = service.bindSina(params.getParameter("sina_uid"),params.getParameter("sina_access_token"),params.getParameter("sina_expires_in"),params.getParameter("user_id"));
        ResponseResults results = new ResponseResults(params);
        results.putResult("bind_flag", result);
        return results;
    }
}
