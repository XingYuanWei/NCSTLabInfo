package com.umi361.dao.userInfo;

import com.umi361.domain.userInfo.UserInfoMain;
import org.icesea.genericDAO.DataSourceProvider.DataSourceInitializer;
import org.icesea.genericDAO.orm.ExtensibleGenericEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserInfoMainDAO extends ExtensibleGenericEntityMapper<UserInfoMain> {
    @Autowired
    public UserInfoMainDAO(DataSourceInitializer initializer) {
        super(initializer);
    }

}
