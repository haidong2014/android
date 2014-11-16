package com.infodeliver.webservice.recipient.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.infodeliver.webservice.common.service.AbstractBaseService;
import com.infodeliver.webservice.recipient.dao.IRecipientDao;
import com.infodeliver.webservice.recipient.service.IRecipientService;

public class RecipientService extends AbstractBaseService implements
		IRecipientService {

	@Autowired
	private IRecipientDao recipientDao;

	@Override
	@Transactional(propagation = Propagation.NEVER, readOnly = true)
	public List<Map<String, Object>> getRecipientStatusList(int noticeNo,
			String userId) {
		// get recipient list
		List<Map<String, Object>> list = recipientDao.getRecipientStatusList(noticeNo,userId);

		return list;
	}

}
