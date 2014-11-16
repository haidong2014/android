package com.infodeliver.webservice.push.dao;

import java.util.List;
import java.util.Map;

public interface IPushDao {

	Map<String, Object> getPushNoticeCount(String userId);
	
	List<Map<String, Object>> getPushNoticeList(String userId);
}
