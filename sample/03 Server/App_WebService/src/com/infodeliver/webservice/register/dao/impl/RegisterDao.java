package com.infodeliver.webservice.register.dao.impl;

import java.util.Map;

import com.infodeliver.webservice.common.dao.AbstractBaseDao;
import com.infodeliver.webservice.register.dao.IRegisterDao;

public class RegisterDao extends AbstractBaseDao implements IRegisterDao{

    @Override
    public Map<String, Object> getLastCaptcha(String phone) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" (SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" T_CAPTCHA ");
        sql.append(" WHERE ");
        sql.append(" DEL_FLAG = 0 ");
        sql.append(" AND TEL = ? ");
        sql.append(" ORDER BY CREATE_DATE DESC) T ");
        sql.append(" GROUP BY TEL ");

        return queryForMap(sql.toString(), new Object[]{phone});
    }

    @Override
    public int register(String phone, String password, String nickname) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ");
        sql.append(" T_USER ");
        sql.append(" (USER_TEL, USER_PASS, USER_NAME, LAST_LOGIN_DATE)");
        sql.append(" VALUES ");
        sql.append(" ( ?, ?, ?, now())");
        return update(sql. toString(), new Object[]{phone, password, nickname});
    }
}
