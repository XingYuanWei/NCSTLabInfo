package com.umi361._wechatBasicService.accessToken;

import com.umi361._globalConstants.GlobalConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;


@ContextConfiguration("classpath:spring.xml")
public class TestAccessTokenAuthenticator extends AbstractTestNGSpringContextTests {
    @Autowired
    private GlobalConstants globalConstants;

    @Test
    public void test() {
        System.out.println(globalConstants.getAccessToken());
    }
}
