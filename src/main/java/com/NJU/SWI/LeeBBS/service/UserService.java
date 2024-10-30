package com.NJU.SWI.LeeBBS.service;

import ch.qos.logback.core.util.StringUtil;
import com.NJU.SWI.LeeBBS.dao.UserMapper;
import com.NJU.SWI.LeeBBS.entity.User;
import com.NJU.SWI.LeeBBS.util.BBSUtil;
import com.NJU.SWI.LeeBBS.util.LeeBBSConstant;
import com.NJU.SWI.LeeBBS.util.MailClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.*;

@Service
public class UserService implements LeeBBSConstant {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MailClient mailClient;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Value("${server.servlet.context-path}")
    private String contextPath;
    @Value("${LeeBBS.path.domain}")
    private String domain;

    public User findUserById(int id) {
        return userMapper.selectById(id);
    }

    /**
     * 注册用户
     * @param user
     * @return Map<String,Object>,存储错误信息或者注册成功
     */
    public Map<String,Object> register(User user) {
//        System.out.println("HELLO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        Map<String,Object> resMap = new HashMap<>();
        if(user == null){
            throw new RuntimeException("用户不能为空");
        }else if(StringUtils.isBlank(user.getUserName())){
            resMap.put("usernameMsg","用户名不能为空");
            return resMap;
        } else if (StringUtils.isBlank(user.getPassword())) {
            resMap.put("passwordMsg","密码不能为空");
            return resMap;
        } else if (StringUtils.isBlank(user.getEmail())) {
            resMap.put("emailMsg","邮箱不能为空");
            return resMap;
        }else if(userMapper.selectByName(user.getUserName()) != null){
            resMap.put("usernameMsg","用户名已被注册");
            return resMap;
        }
        String salt = BBSUtil.generateUUID().substring(1,7);
        user.setSalt(salt);
        user.setPassword(BBSUtil.md5(user.getPassword(),salt));
        user.setType(0);
        user.setStatus(0);
        user.setActivationCode(BBSUtil.generateUUID());
        user.setHeaderUrl(String.format(
                "http://images.nowcoder.com/head/%dt.png",
                new Random().nextInt(1000))
        );
        user.setCreateTime(new Date());
        logger.trace("Register Params Checked, About to insert into db...");
        userMapper.insertUser(user);
        logger.trace("Register Successful, About to send email...");
        Context context = new Context();
        context.setVariable("email",user.getEmail());
//      http://localhost:8888/LeeBBS/activation/{userId}/activationCode
        // context-path = /LeeBBS  domain= http://localhost:8888
        String url = domain + contextPath + "/activation/"+ user.getId() +
                "/" + user.getActivationCode();
        context.setVariable("url",url);
        String content = templateEngine.process("/mail/activation",context);
        mailClient.sendMail(user.getEmail(),"激活账号",content);
        logger.trace("Mail send, account await to be activated...");
        resMap.put("success","注册成功，请检查邮箱以激活账号");
        return resMap;
    }
    public int activation(int userId,String code) {
        User user = userMapper.selectById(userId);
        if(user.getStatus() == 1){
            return ACTIVE_REPEAT;
        }else if(user.getActivationCode().equals(code)){
            userMapper.updateStatus(userId,1);
            return ACTIVE_SUCCESS;
        }else {
            return ACTIVE_FAIL;
        }
    }

    public int updateHeader(int userId, String headerUrl){
        return userMapper.updateHeaderUrl(userId,headerUrl);
    }



}
