package com.infodeliver.webservice.common.service;

import org.apache.log4j.Logger;

public abstract class AbstractBaseService {

    private static Logger baseLogger = Logger.getLogger(AbstractBaseService.class);

    protected Logger logger = Logger.getLogger(this.getClass());
}
