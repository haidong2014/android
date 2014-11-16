package com.infodeliver.webservice.push.service;

import java.util.List;
import java.util.Map;

public interface IPushService {

	Map<String, Object> getPushNoticeCount(String userId);
	
	List<Map<String, Object>> getPushNoticeList(String userId);
}
