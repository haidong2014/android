package com.infodeliver.webservice.recipient.service;

import java.util.List;
import java.util.Map;

public interface IRecipientService {
	
	List<Map<String, Object>> getRecipientStatusList(int noticeNo, String userId);
}
