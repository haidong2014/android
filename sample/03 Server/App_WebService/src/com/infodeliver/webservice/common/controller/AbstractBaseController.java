package com.infodeliver.webservice.common.controller;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.infodeliver.webservice.common.util.ResourceUtil;

public abstract class AbstractBaseController {

    private static Logger baseLogger = Logger.getLogger(AbstractBaseController.class);

    protected Logger logger = Logger.getLogger(this.getClass());

    private static final String RESOURCE_SERVICE = "service.properties";

    protected String serviceExecMode(){
        Properties prop = ResourceUtil.reloadProperties(RESOURCE_SERVICE);
        return prop.getProperty("service.execution.mode");
    }
}
