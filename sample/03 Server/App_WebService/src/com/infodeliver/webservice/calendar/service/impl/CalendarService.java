package com.infodeliver.webservice.calendar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.infodeliver.webservice.calendar.dao.ICalendarDao;
import com.infodeliver.webservice.calendar.service.ICalendarService;
import com.infodeliver.webservice.common.service.AbstractBaseService;

public class CalendarService extends AbstractBaseService implements ICalendarService {

    @Autowired
    private ICalendarDao dao;
}
