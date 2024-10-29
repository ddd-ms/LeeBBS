package com.NJU.SWI.LeeBBS.entity;

import com.NJU.SWI.LeeBBS.util.BBSUtil;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;
@Entity
@Data
public class LoginTicket {
    @GeneratedValue
    @Id
    private int id;
    private int userId;
    private String ticket;
    private int status;
    private Date expired;
    public LoginTicket(){}
    public LoginTicket(User u, long expired){
        this.userId = u.getId();
        this.ticket = BBSUtil.generateUUID();
        this.status = 0;
        this.expired = new Date(System.currentTimeMillis()+expired*1000);
    }
}
