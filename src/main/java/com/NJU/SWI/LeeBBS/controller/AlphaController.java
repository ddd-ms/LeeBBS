package com.NJU.SWI.LeeBBS.controller;

import com.NJU.SWI.LeeBBS.dao.AlphaDao;
import com.NJU.SWI.LeeBBS.util.BBSUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/alpha-controller")
public class AlphaController {
    @RequestMapping("/greetings")
    @ResponseBody
    public String greetings(){
        return "let's go spring! from 1215";
    }
    @Autowired
    private AlphaDao alphaDao;
    @RequestMapping("/data")
    @ResponseBody
    public String testAlpha(){
        return alphaDao.select();
    }

    @RequestMapping("/http")
    public void hello(HttpServletRequest req, HttpServletResponse resp){
//        System.out.println(req);
//        System.out.println(resp);
        System.out.println(req.getMethod());
        System.out.println(req.getServletPath());
        System.out.println(req.getParameter("code"));

        resp.setContentType("text/html;charset=utf-8");
        try(PrintWriter writer = resp.getWriter()){
            writer.write("<h1>GOGOGO<h1/>");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @RequestMapping(path = "/students",method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(@RequestParam(value = "limit",defaultValue = "0",required = false) int limit){
        return limit + "<h1>GET students</h1>";
    }
    @RequestMapping(path = "/students", method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name,int age){
        System.out.println(name + " " + age);
        return "success";
    }
    @RequestMapping(path = "/students/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id") int id){
//        System.out.println("met get id");
        return id + "<h1>GET student</h1>";
    }

//    Response to Browser with HTML
    @RequestMapping(path = "/teacher",method = RequestMethod.GET)
    public ModelAndView getTeacher(@RequestParam(value = "id",required = false,defaultValue = "0") int id){
        ModelAndView mav = new ModelAndView();
        mav.addObject("id",id);
        mav.addObject("name","Lee");
        mav.addObject("age",18);
        mav.setViewName("/demo/view");
        return mav;
    }

    @RequestMapping(value = "/school", method =RequestMethod.GET)
    public String getSchool(Model model){
        model.addAttribute("name","NJU");
        // Check warnings
        return "/demo/view";
    }

    // Response to Browser with JSON
    @RequestMapping("/emp")
    @ResponseBody
    public Map<String,Object> getEmp(){
        return Map.of("name","Lee","age",18);
    }

    @RequestMapping("/getAllEmps")
    @ResponseBody
    public List<Map<String,Object>> getAllEmps(){
        return List.of(
                Map.of("name","L1","age",22),
                Map.of("name","L2","age",28)
        );
    }

    // Cookie Relevant
    @RequestMapping(path = "/cookie/set",method = RequestMethod.GET)
    @ResponseBody
    public String setCookie(HttpServletResponse resp){
        Cookie cookie = new Cookie("code", "12311111111111111");
        cookie.setPath("/LeeBBS/alpha-controller");
        cookie.setMaxAge(6000);
        resp.addCookie(cookie);
        return "set cookie";

    }
    @RequestMapping(path = "/cookie/get",method = RequestMethod.GET)
    @ResponseBody
    public String getCookie(HttpServletRequest req){
        Cookie[] cookies = req.getCookies();
        if (cookies == null){
            return "no cookie";
        }
        for(Cookie cookie : cookies){
            System.out.println(cookie.getName() + ":" + cookie.getValue());
        }
        return "get cookie: ";
    }
    @RequestMapping(value = "/cookie/getp",method = RequestMethod.GET)
    @ResponseBody
    public String getCookieP(@CookieValue("code") String code){
        return "get cookie: " + code;
    }

    @RequestMapping(value = "/session/set",method = RequestMethod.GET)
    @ResponseBody
    public String setSession(HttpSession session){
        session.setAttribute("code","123456");
        return "set session";
    }
    @RequestMapping(value = "/session/get",method = RequestMethod.GET)
    @ResponseBody
    public String getSession(HttpSession session){
        return "get session: " + session.getAttribute("code");
    }

    @RequestMapping(value ="testAjax")
    @ResponseBody
    public String testAjax(String name,int age){
        return BBSUtil.jsonDumps(200,"success",Map.of("name",name,"age",age));
    }
}
