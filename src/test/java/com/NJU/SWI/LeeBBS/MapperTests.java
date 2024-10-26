package com.NJU.SWI.LeeBBS;

import com.NJU.SWI.LeeBBS.dao.UserMapper;
import com.NJU.SWI.LeeBBS.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = LeeBbsApplication.class)
public class MapperTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectUser(){
        System.out.println(userMapper.selectById(101));
        System.out.println(userMapper.selectByName("liubei"));
    }
    @Test
    public void insertUser(){
        User user = new User("zhangsan","123",null,null,0,0,"0",null,null);
        int effected = userMapper.insertUser(user);
        System.out.println(effected);
    }
}
