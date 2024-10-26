package com.NJU.SWI.LeeBBS.controller;

import com.NJU.SWI.LeeBBS.dao.AlphaDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
        try(PrintWriter writer = resp.getWriter();){
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
}
