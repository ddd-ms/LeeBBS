package com.NJU.SWI.LeeBBS.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;
@Entity
@Data
public class User {
    @Id
    @GeneratedValue
    private int id;
    private String userName;
    private String password;
    private String salt;
    private String email;
    private int type;
    private int status;
    private String activationCode;
    private String headerUrl;
    private Date createTime;

    public User( String userName, String password, String salt, String email, int type, int status, String activationCode, String headerUrl, Date createTime) {
        this.userName = userName;
        this.password = password;
        this.salt = salt;
        this.email = email;
        this.type = type;
        this.status = status;
        this.activationCode = activationCode;
        this.headerUrl = headerUrl;
        this.createTime = createTime;
    }

    public User() {}
}
