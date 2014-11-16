package com.infodeliver.webservice.captcha.dao.impl;

import java.util.Map;

import com.infodeliver.webservice.captcha.dao.ICaptchaDao;
import com.infodeliver.webservice.common.dao.AbstractBaseDao;

public class CaptchaDao extends AbstractBaseDao implements ICaptchaDao {

    @Override
    public void insert(String phone, int captcha) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ");
        sql.append(" T_CAPTCHA ");
        sql.append(" (TEL, CAPTCHA) ");
        sql.append(" VALUES( ?, ? ) ");
        update(sql.toString(), new Object[]{phone, captcha});
    }

    @Override
    public Map<String, Object> getPhone(String phone) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(" COUNT(ID) CNT ");
        sql.append(" FROM ");
        sql.append(" T_USER ");
        sql.append(" WHERE ");
        sql.append(" USER_TEL = ? ");
        return queryForMap(sql.toString(),  new Object[]{phone});
    }
}
