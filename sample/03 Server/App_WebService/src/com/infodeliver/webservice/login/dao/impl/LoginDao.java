package com.infodeliver.webservice.login.dao.impl;

import java.util.Map;

import com.infodeliver.webservice.common.dao.AbstractBaseDao;
import com.infodeliver.webservice.login.dao.ILoginDao;

public class LoginDao extends AbstractBaseDao implements ILoginDao {

    @Override
    public Map<String, Object> getUserByTel(String phone, String pwd) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(" * ");
        sql.append(" FROM T_USER ");
        sql.append(" WHERE ");
        sql.append(" USER_TEL = ? ");
        sql.append(" AND USER_PASS = ? ");

        return queryForMap(sql.toString(), new Object[]{phone, pwd});
    }

    @Override
    public int updatePwdByTel(String phone, String pwd) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ");
        sql.append(" T_USER ");
        sql.append(" SET USER_PASS = ? ");
        sql.append(" WHERE ");
        sql.append(" USER_TEL = ? ");

        return update(sql.toString(), new Object[]{pwd, phone});
    }
}
