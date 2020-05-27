package com.umi361.service.userInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class UserPreferenceService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    DataSource dataSource;

    Long getPreferredLabId(Long userIdLong) {
        Connection connection = null;
        Long collegeId = null;
        Long ret = null;
        try {
            connection = dataSource.getConnection();
            collegeId = jdbcTemplate.queryForObject("SELECT college_id FROM user_info_main WHERE user_id = ?", Long.class, userIdLong);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return collegeId;
    }

}
