package com.NJU.SWI.LeeBBS.controller.interceptor;

import com.NJU.SWI.LeeBBS.entity.LoginTicket;
import com.NJU.SWI.LeeBBS.entity.User;
import com.NJU.SWI.LeeBBS.service.LoginService;
import com.NJU.SWI.LeeBBS.service.UserService;
import com.NJU.SWI.LeeBBS.util.HostHolder;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class LoginTicketInterceptor implements HandlerInterceptor {
    @Autowired
    private LoginService loginService;
    @Autowired
    private UserService userService;
    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
//      if 'ticket' in [k for k in cookies.getKeys()]:
        String ticket = null;
        if (cookies != null){
            for (Cookie cookie : cookies){
                System.out.println(cookie.getName());
                if (cookie.getName().equals("ticket")){
                    ticket = cookie.getValue();
                }
            }
        }
        if (ticket != null){
            LoginTicket t = loginService.loginTicket(ticket);
            if (t != null && t.getStatus() == 0
                    && t.getExpired().after(new java.util.Date())){
                User u = userService.findUserById(t.getUserId());
                // 暂存到session给本次服务用
                request.getSession().setAttribute("user", u);
                hostHolder.setUser(u);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView){
        User u = hostHolder.getUser();
        if(u!=null && modelAndView!=null){
            modelAndView.addObject("loginUser", u);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}
