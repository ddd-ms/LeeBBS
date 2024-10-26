package com.NJU.SWI.LeeBBS.service;

import com.NJU.SWI.LeeBBS.dao.UserMapper;
import com.NJU.SWI.LeeBBS.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public User findUserById(int id) {
        return userMapper.selectById(id);
    }
}
