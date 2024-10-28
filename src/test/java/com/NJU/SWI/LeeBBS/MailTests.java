package com.NJU.SWI.LeeBBS;

import com.NJU.SWI.LeeBBS.util.MailClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = LeeBbsApplication.class)
public class MailTests {
    @Autowired
    private MailClient mailClient;
    @Autowired
    private TemplateEngine templateEngine;
    @Test
    public void testTextMail(){
        mailClient.sendMail("1336947958@qq.com","❤❤❤❤❤❤❤❤","啵啵啵啵啵啵啵啵");
    }
    @Test
    public void testHtmlMail(){
//      手动调用模板引擎渲染动态页面
        Context context=new Context();
        context.setVariable("username","sunday");
        String content=templateEngine.process("/mail/demo",context);
        mailClient.sendMail("2541684445@qq.com","HtmlMailForTest",content);
    }
}

