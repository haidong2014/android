package com.infodeliver.webservice.user.dao.impl;

import java.util.Map;

import com.infodeliver.webservice.common.dao.AbstractBaseDao;
import com.infodeliver.webservice.user.dao.IUserDao;

public class UserDao extends AbstractBaseDao implements IUserDao {

    @Override
    public int updateForSex(String sex, String userId) {
        return modifyById("USER_SEX", sex, userId);
    }

    @Override
    public Map<String, Object> getUserById(String userId, String password) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(" * ");
        sql.append(" FROM T_USER ");
        sql.append(" WHERE ");
        sql.append(" ID = ? ");
        sql.append(" AND USER_PASS = ? ");

        return queryForMap(sql.toString(), new Object[]{userId, password});
    }

    @Override
    public int modifyNickname(String nickname, String userId) {
        return modifyById("USER_NAME", nickname, userId);
    }

    @Override
    public int modifySign(String sign, String userId) {
        return modifyById("USER_SIGN", sign, userId);
    }

    @Override
    public int modifyPwd(String password, String userId) {
        return modifyById("USER_PASS", password, userId);
    }

    @Override
    public int modifyPhone(String phone, String userId) {
        return modifyById("USER_TEL", phone, userId);
    }

    @Override
    public Map<String, Object> getUserByPhone(String userId, String phone) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(" * ");
        sql.append(" FROM T_USER ");
        sql.append(" WHERE ");
        sql.append(" ID = ? ");
        sql.append(" AND USER_TEL = ? ");

        return queryForMap(sql.toString(), new Object[]{userId, phone});
    }

    @Override
    public int bindSina(String sinaUid, String sinaToken, String sinaExpires,
            String userId) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ");
        sql.append(" T_USER ");
        sql.append(" SET SINA_UID = ?, ");
        sql.append(" SINA_ACCESS_TOKEN = ?, ");
        sql.append(" SINA_EXPIRES_IN = ?, ");
        sql.append(" WHERE ");
        sql.append(" ID = ? ");

        return update(sql.toString(), new Object[]{sinaUid, sinaToken, sinaExpires, userId});
    }

    private int modifyById(String modifyCol, String modifyVal, String userId) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ");
        sql.append(" T_USER ");
        sql.append(" SET ");
        sql.append(modifyCol);
        sql.append(" = ? ");
        sql.append(" WHERE ");
        sql.append(" ID = ? ");

        return update(sql.toString(), new Object[]{modifyVal, userId});
    }

}
