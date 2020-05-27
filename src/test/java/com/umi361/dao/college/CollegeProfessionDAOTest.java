package com.umi361.dao.college;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@ContextConfiguration("classpath:spring-test.xml")
public class CollegeProfessionDAOTest extends AbstractTestNGSpringContextTests {
    @Autowired
    CollegeProfessionDAO collegeProfessionDAO;

    @BeforeMethod
    public void setUp() {
    }

    @Test
    public void getProfessionCollegePairsData() {
        System.out.println(collegeProfessionDAO.getProfessionCollegePairListMap());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme