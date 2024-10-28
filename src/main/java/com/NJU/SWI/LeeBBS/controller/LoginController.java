package com.NJU.SWI.LeeBBS.controller;

import com.NJU.SWI.LeeBBS.entity.User;
import com.NJU.SWI.LeeBBS.service.UserService;
import com.NJU.SWI.LeeBBS.util.LeeBBSConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
public class LoginController implements LeeBBSConstant {
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
}
