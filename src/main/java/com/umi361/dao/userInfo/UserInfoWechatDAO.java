package com.umi361.dao.userInfo;


import com.umi361.domain.userInfo.UserInfoWechat;
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
public class UserInfoWechatDAO extends ExtensibleGenericEntityMapper<UserInfoWechat> {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private Logger logger = LogManager.getLogger(getClass());

    @Autowired
    public UserInfoWechatDAO(DataSourceInitializer initializer) {
        super(initializer);
    }

    public boolean checkExistenceByOpenid(String openid) {
        int count = jdbcTemplate.queryForObject("SELECT count(*) FROM user_info_wechat WHERE openid = ?", Integer.class, openid);
        if (count > 0 && count != 1) logger.error(new Date() + "\tDuplicate openid detected in user_info_wechat for openid : " + openid);
        return count > 0;
    }

    public UserInfoWechat getUserInfoWechatByOpenid(String openid) throws SQLException {
        return getEntity("SELECT * FROM user_info_wechat WHERE openid = ?", openid);
    }

}
