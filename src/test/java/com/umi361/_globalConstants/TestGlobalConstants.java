package com.umi361._globalConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;


@ContextConfiguration("classpath:spring.xml")
public class TestGlobalConstants extends AbstractTestNGSpringContextTests {
    @Autowired
    private GlobalConstants globalConstants;

    @Test
    public void test() {
        System.out.println(globalConstants.getToken());
        assertTrue(globalConstants.getToken() != null);
    }
}
