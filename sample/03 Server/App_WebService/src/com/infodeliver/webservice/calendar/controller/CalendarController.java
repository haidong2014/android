package com.infodeliver.webservice.calendar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.infodeliver.webservice.calendar.service.ICalendarService;
import com.infodeliver.webservice.common.controller.AbstractBaseController;

@Controller
public class CalendarController extends AbstractBaseController {

    @Autowired
    private ICalendarService service;
}
