package com.NJU.SWI.LeeBBS.dao;

import com.NJU.SWI.LeeBBS.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface UserMapper {
    User selectById(int id);
    User selectByName(String name);
    int insertUser(User user);
    int updateUser(User user);
    int updateStatus(int id,int status);
    int updateHeaderUrl(int id,String headerUrl);
    int updatePassword(int id,String password);
    int selectCount();
}
