package com.infodeliver.webservice.common.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public abstract class AbstractBaseDao extends JdbcDaoSupport {

    private static Logger baseLogger = Logger.getLogger(AbstractBaseDao.class);

    protected Logger logger = Logger.getLogger(this.getClass());

    protected List<Map<String, Object>> queryForList(String sql, Object... params){
        return getJdbcTemplate().queryForList(sql, params);
    }

    protected Map<String, Object> queryForMap(String sql, Object... params){
        try {
            return getJdbcTemplate().queryForMap(sql, params);
        } catch (EmptyResultDataAccessException e) {
            // TODO
            logger.info(sql + params + "未取到");
            return new HashMap<String, Object>();
        } catch (IncorrectResultSizeDataAccessException e) {
            // TODO
            logger.info(sql + params + "取到多件");
            return new HashMap<String, Object>();
        }
    }

    protected int update(String sql, Object... params){
        return getJdbcTemplate().update(sql, params);
    }
}
