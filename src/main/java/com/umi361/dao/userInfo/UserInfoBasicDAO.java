package com.umi361.dao.userInfo;

import com.umi361.domain.userInfo.UserInfoBasic;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.icesea.genericDAO.DataSourceProvider.DataSourceInitializer;
import org.icesea.genericDAO.orm.ExtensibleGenericEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Date;

@Repository
public class UserInfoBasicDAO extends ExtensibleGenericEntityMapper<UserInfoBasic> {
    private Logger logger = LogManager.getLogger(getClass());
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    public UserInfoBasicDAO(DataSourceInitializer initializer) {
        super(initializer);
    }

    public boolean checkOpenidValid(String openid) {
        int ret = jdbcTemplate.queryForObject("SELECT count(*) FROM user_info_basic WHERE openid = ?", Integer.class, openid);
        if (ret > 0 && ret != 1) {
            logger.error(new Date() + "\topenid inside database is not unique for : " + openid);
        }
        return ret > 0;
    }

    public boolean checkRegisteredByOpenid(String openid) {
        return jdbcTemplate.queryForObject("SELECT registered FROM user_info_basic WHERE openid = ?", Boolean.class, openid);
    }

    public Long getUserIdByOpenid(String openid) {
        return jdbcTemplate.queryForObject("SELECT user_id FROM user_info_basic WHERE openid = ?", Long.class, openid);
    }

    public void registerDirectlyByOpenid(String openid) {
        jdbcTemplate.update("UPDATE user_info_basic SET registered = TRUE WHERE openid = ?", openid);
    }

    public void addUserByOpenidINExists(String openid) {
        // dual 为虚拟表，当且仅当 WHERE 子句成立时 dual 的 SELECT 才返回
        jdbcTemplate.update("INSERT INTO user_info_basic (openid) SELECT ? FROM dual WHERE NOT EXISTS(" +
                "SELECT openid FROM user_info_basic WHERE openid = ?)", openid, openid);
    }
}
