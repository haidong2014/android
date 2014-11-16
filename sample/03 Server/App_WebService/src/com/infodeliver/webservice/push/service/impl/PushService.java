package com.infodeliver.webservice.push.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.infodeliver.webservice.common.service.AbstractBaseService;
import com.infodeliver.webservice.push.dao.IPushDao;
import com.infodeliver.webservice.push.service.IPushService;

public class PushService extends AbstractBaseService implements
		IPushService {

	@Autowired
	private IPushDao pushDao;

	@Override
	@Transactional(propagation = Propagation.NEVER, readOnly=true)
	public Map<String, Object> getPushNoticeCount(String userId) {
		Map<String, Object> pushNoticeMap =  pushDao.getPushNoticeCount(userId);
		return pushNoticeMap;
	}

	@Override
	@Transactional(propagation = Propagation.NEVER, readOnly=true)
	public List<Map<String, Object>> getPushNoticeList(String userId) {
		List<Map<String, Object>> pushNoticeList =  pushDao.getPushNoticeList(userId);
		return pushNoticeList;
	}


}
