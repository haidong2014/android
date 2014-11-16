package com.infodeliver.webservice.login.controller;

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
import com.infodeliver.webservice.login.service.ILoginService;

@Controller
public class LoginController extends AbstractBaseController {

    @Autowired
    private ILoginService loginService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody ResponseResults login(@RequestBody RequestParameters params)
    {
        logger.debug("phone:"+params.getParameter("phone"));
        logger.debug("password:"+params.getParameter("password"));

        Map<String, Object> result = loginService.login(params.getParameter("phone"),params.getParameter("password"));
        ResponseResults results = new ResponseResults(params);
        results.setParams(result);
        return results;
    }

    @RequestMapping(value = "/password_reset", method = RequestMethod.POST)
    public @ResponseBody ResponseResults passwordReset(@RequestBody RequestParameters params)
    {
        logger.debug("phone:"+params.getParameter("phone"));
        logger.debug("password:"+params.getParameter("password"));

        boolean resultState = loginService.passwordReset(params.getParameter("phone"),params.getParameter("password"));
        ResponseResults results = new ResponseResults(params);
        results.putResult("is_reset", resultState);
        return results;
    }
}
