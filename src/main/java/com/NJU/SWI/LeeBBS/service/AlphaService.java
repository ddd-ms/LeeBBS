package com.NJU.SWI.LeeBBS.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
//@Scope("prototype") -- 每次获取时创建新的Bean对象，默认单例—>@Scope("singleton")
public class AlphaService {
    public AlphaService() {
        System.out.println("Construct AlphaService");
    }

    @PostConstruct
    public void init(){
        System.out.println("INIT AlphaService");
    }
    @PreDestroy
    public void destroy(){
        System.out.println("DESTROY AlphaService");
    }
}
