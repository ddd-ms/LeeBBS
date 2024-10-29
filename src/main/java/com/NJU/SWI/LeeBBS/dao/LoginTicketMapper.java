package com.NJU.SWI.LeeBBS.dao;

import com.NJU.SWI.LeeBBS.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

import java.util.Date;

@Mapper
public interface LoginTicketMapper {
    @Insert("insert into login_ticket(user_id,ticket,status,expired) " +
            "values (#{userId},#{ticket},#{status},#{expired})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int insertLoginTicket(LoginTicket loginTicket);
    @Select("select id,user_id,ticket,status,expired " +
            "from login_ticket " +
            "where ticket=#{loginTicket}")
    LoginTicket selectByTicket(String ticket);
    @Update("update login_ticket set status = #{status} where ticket=#{ticket}")
    int updateStatus(String ticket,int status);

}
