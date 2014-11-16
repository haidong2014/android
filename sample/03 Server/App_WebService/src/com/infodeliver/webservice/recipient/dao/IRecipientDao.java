package com.infodeliver.webservice.recipient.dao;

import java.util.List;
import java.util.Map;

public interface IRecipientDao {

	List<Map<String, Object>> getRecipientStatusList(int noticeNo, String userId);
}
