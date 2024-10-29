package com.NJU.SWI.LeeBBS.service;

import com.NJU.SWI.LeeBBS.dao.LoginTicketMapper;
import com.NJU.SWI.LeeBBS.dao.UserMapper;
import com.NJU.SWI.LeeBBS.entity.LoginTicket;
import com.NJU.SWI.LeeBBS.entity.User;
import com.NJU.SWI.LeeBBS.util.BBSUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {
    @Autowired
    private LoginTicketMapper loginTicketMapper;
    @Autowired
    private UserMapper userMapper;

    public Map<String, Object> login(String username, String password, long expireTime) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (StringUtils.isBlank(username)) {
            result.put("usernameMsg", "账号不能为空");
            return result;
        } else if (StringUtils.isBlank(password)) {
            result.put("passwordMsg", "密码不能为空");
            return result;
        }
        // 验证账号
        User user = userMapper.selectByName(username);
        if (user == null) {
            result.put("usernameMsg", "该账号不存在");
            return result;
        }
        if (user.getStatus() == 0) {
            result.put("usernameMsg", "该账号未激活");
            return result;
        }
        // 验证密码
        if (user.getPassword().equals(BBSUtil.md5(password, user.getSalt()))) {
            result.put("passwordMsg", "密码不正确");
            return result;
        }
        LoginTicket loginTicket = new LoginTicket(user, expireTime);
        loginTicketMapper.insertLoginTicket(loginTicket);
        result.put("ticket", loginTicket.getTicket());
        return result;
    }
    public int logout(String ticket) {
        return loginTicketMapper.updateStatus(ticket, 1);
    }
    public LoginTicket loginTicket(String ticket){
        return loginTicketMapper.selectByTicket(ticket);
    }
}
