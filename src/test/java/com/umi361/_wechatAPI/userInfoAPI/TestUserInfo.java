package com.umi361._wechatAPI.userInfoAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@ContextConfiguration("classpath:spring.xml")
public class TestUserInfo extends AbstractTestNGSpringContextTests {

    @Autowired
    APIUserInfoWechat apiUserInfoWechat;

    @Test
    public void getOpenidByCode() {
    }

    @Test
    public void grabUserInfoByOpenid() {
        System.out.println(apiUserInfoWechat.grabUserInfoWechatByOpenid("oHb0a1tc7eKeSTzSJO8_M70uOYIk"));
    }
}
