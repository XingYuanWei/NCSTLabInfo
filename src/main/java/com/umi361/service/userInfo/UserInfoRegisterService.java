package com.umi361.service.userInfo;

import com.umi361.dao.userInfo.UserInfoBasicDAO;
import com.umi361.dao.userInfo.UserInfoMainDAO;
import com.umi361.domain.userInfo.UserInfoMain;
import org.icesea.genericDAO.GenericValuePooledDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;

@Service
public class UserInfoRegisterService {
    @Autowired
    UserInfoBasicDAO userInfoBasicDAO;
    @Autowired
    UserInfoMainDAO userInfoMainDAO;
    @Autowired
    GenericValuePooledDAO genericValuePooledDAO;

    public void RegisterWithUserInfo(String openid, String studentId, long majorId, String realName, int sex,
                                     long[] speciality, long[] interest) throws SQLException {
        Connection connection = genericValuePooledDAO.getConnection();
        Long user_id;
        GenericValuePooledDAO.startTx(connection);
        try {
            userInfoBasicDAO.update(connection,"UPDATE user_info_basic SET registered = TRUE WHERE openid = ?", openid);
            user_id = genericValuePooledDAO.getSingleValue(connection, "SELECT user_id FROM user_info_basic WHERE openid = ?", openid);
            Long collegeId = genericValuePooledDAO.getSingleValue(connection, "SELECT college_id FROM profession_list WHERE profession_id = ?", majorId);
            if (collegeId == null) throw new RuntimeException();
            userInfoMainDAO.mapInsertSingleObject(connection, new UserInfoMain(user_id, studentId, realName, collegeId, majorId));
            userInfoMainDAO.update(connection, "UPDATE user_info_wechat SET sex = ? WHERE user_id = ?", sex, user_id);
        } catch (Exception e) {
            GenericValuePooledDAO.rollbackTx(connection);
            throw new SQLException();
        }
        GenericValuePooledDAO.commitTx(connection);
        connection = genericValuePooledDAO.getConnection();
        try {
            for (Long each : speciality) {
                userInfoMainDAO.update(connection, "INSERT INTO user_data_speciality (user_id, speciality_id) VALUES (?, ?)", user_id, each);
            }
            for (Long each : interest) {
                userInfoMainDAO.update(connection, "INSERT INTO user_data_interest (user_id, interest_id) VALUES (?, ?)", user_id, each);
            }
        } catch (Exception e) {
            GenericValuePooledDAO.rollbackTx(connection);
            throw new SQLException();
        }
        GenericValuePooledDAO.commitTx(connection);
    }

}
