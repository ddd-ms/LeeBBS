package com.NJU.SWI.LeeBBS;

import com.NJU.SWI.LeeBBS.dao.DiscussPostMapper;
import com.NJU.SWI.LeeBBS.dao.LoginTicketMapper;
import com.NJU.SWI.LeeBBS.dao.UserMapper;
import com.NJU.SWI.LeeBBS.entity.LoginTicket;
import com.NJU.SWI.LeeBBS.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = LeeBbsApplication.class)
public class MapperTests {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LoginTicketMapper loginTicketMapper;
    @Test
    public void testLoginTicketMapper(){
        LoginTicket t = new LoginTicket();
        t.setUserId(101);
        t.setTicket("abc");
        t.setStatus(0);
        t.setExpired(new Date(System.currentTimeMillis()+1000*60*10));
        loginTicketMapper.insertLoginTicket(t);
    }
    @Test public void testSelectLoginTicket(){
        LoginTicket loginTicket = loginTicketMapper.selectByTicket("abc");
        System.out.println(loginTicket);
    }
    @Test
    public void testUpdateLoginTicket() {
        loginTicketMapper.updateStatus("abc",1);
    }

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
    @Autowired
    private DiscussPostMapper discussPostMapper;
    @Test
    public void testDiscussPost(){
        System.out.println(discussPostMapper.selectCount(0));
        System.out.println(discussPostMapper.selectDiscussPostList(0,0,10));
    }
}
