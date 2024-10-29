package com.NJU.SWI.LeeBBS.config;

import com.NJU.SWI.LeeBBS.controller.interceptor.AlphaInterceptor;
import com.NJU.SWI.LeeBBS.controller.interceptor.LoginTicketInterceptor;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {
    @Resource
    private AlphaInterceptor alphaInterceptor;
    @Autowired
    private LoginTicketInterceptor loginTicketInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(alphaInterceptor)
//                .excludePathPatterns("/**/*.css","/**/*.js","/**/*.png","/**/*.jpg")
//                .addPathPatterns("/register","/login");
        registry.addInterceptor(loginTicketInterceptor)
                .excludePathPatterns("/**/*.css","/**/*.js","/**/*.png","/**/*.jpg");

    }
}
