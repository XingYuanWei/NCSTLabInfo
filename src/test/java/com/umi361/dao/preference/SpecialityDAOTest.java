package com.umi361.dao.preference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@ContextConfiguration("classpath:spring-test.xml")
public class SpecialityDAOTest extends AbstractTestNGSpringContextTests {
    @Autowired
    SpecialityDAO specialityDAO;

    @Test
    public void getSpecialitiesTest() {
        System.out.println(specialityDAO.getSpecialityPairsList());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme