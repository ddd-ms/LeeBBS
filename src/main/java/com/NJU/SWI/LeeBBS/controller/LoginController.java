package com.NJU.SWI.LeeBBS.controller;

import com.NJU.SWI.LeeBBS.dao.LoginTicketMapper;
import com.NJU.SWI.LeeBBS.entity.LoginTicket;
import com.NJU.SWI.LeeBBS.entity.User;
import com.NJU.SWI.LeeBBS.service.LoginService;
import com.NJU.SWI.LeeBBS.service.UserService;
import com.NJU.SWI.LeeBBS.util.LeeBBSConstant;
import com.google.code.kaptcha.Producer;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import java.awt.image.BufferedImage;
import java.util.Map;

@Controller
public class LoginController implements LeeBBSConstant {
    @Autowired
    private LoginService loginService;
    @Value("${server.servlet.context-path}")
    private String contextPath;
    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @RequestMapping(path = "/register",method = RequestMethod.GET)
    public String getRegisterPage(){
        return "/site/register";
    }
    @Autowired
    private UserService userService;
    @RequestMapping(path = "/register",method = RequestMethod.POST)
    public String register(Model model, User user){
        Map<String, Object> map = userService.register(user);
        if(map.containsKey("success")){
            model.addAttribute("msg", map.get("success"));
            model.addAttribute("target","/index");
            return "/site/operate-result";
        }else{
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("passwordMsg", map.get("passwordMsg"));
            model.addAttribute("emailMsg", map.get("emailMsg"));
            return "/site/register";
        }

    }
    //      http://localhost:8888/LeeBBS/activation/{userId}/activationCode
    @RequestMapping(path = "/activation/{userId}/{code}",method = RequestMethod.GET)
    public String activation(Model model,@PathVariable("userId") int id,@PathVariable("code")String code){
        int result = userService.activation(id,code);
        if(result == ACTIVE_SUCCESS){
            model.addAttribute("msg","激活成功，您的账号已经可以使用！");
            model.addAttribute("target","/login");
        }else if(result == ACTIVE_REPEAT){
            model.addAttribute("msg","无效操作，该账号已经激活");
            model.addAttribute("target","/index");
        }else{
            model.addAttribute("msg","激活失败，您提供的账号或者激活码不正确！");
            model.addAttribute("target","/index");
        }
        return "/site/operate-result";
    }
    @RequestMapping(path = "/login",method = RequestMethod.GET)
    public String getLoginPage(){
        return "/site/login";
    }

    @Autowired
    private Producer kaptchaProducer;
    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @RequestMapping(value = "/kaptcha",method = RequestMethod.GET)
    public void getKaptcha(HttpServletResponse response, HttpSession session){
        String text = kaptchaProducer.createText();
        BufferedImage image = kaptchaProducer.createImage(text);
//        BufferedImage image = kaptchaProducer.createImage("sCCCCC");

        session.setAttribute("kaptcha",text);
        response.setContentType("image/png");
        try {
            //输出图片到页面
            javax.imageio.ImageIO.write(image,"png",response.getOutputStream());
        } catch (Exception e) {
            logger.error("响应验证码失败"  + e.getMessage());
        }
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(String username, String password, String code, boolean rememberMe,
                        Model model, HttpSession session, HttpServletResponse resp){
        String kaptcha = (String) session.getAttribute("kaptcha");
        if(StringUtils.isBlank(kaptcha) || StringUtils.isBlank(code) || !kaptcha.equalsIgnoreCase(code)){
            model.addAttribute("codeMsg","验证码不正确");
            return "/site/login";
        }
        Map<String, Object> map = null;
        if (rememberMe) {
            map = loginService.login(username, password, DEFAULT_LOGIN_EXPIRE_TIME);
        }else{
            map = loginService.login(username, password, LONG_LOGIN_EXPIRE_TIME);
        }
        if(map.containsKey("ticket")){
            Cookie cookie = new Cookie("ticket",map.get("ticket").toString());
            cookie.setPath(contextPath);
            cookie.setMaxAge(rememberMe?DEFAULT_LOGIN_EXPIRE_TIME:LONG_LOGIN_EXPIRE_TIME);
            resp.addCookie(cookie);
            model.addAttribute("username",username);
            return "redirect:/index";
        }else {
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("passwordMsg", map.get("passwordMsg"));
            model.addAttribute("codeMsg", map.get("codeMsg"));
            return "/site/login";
        }
    }
    @RequestMapping(path ="/logout")
    public String logout(@CookieValue("ticket") String ticket){
        loginService.logout(ticket);
        return "redirect:/login";
    }

}
