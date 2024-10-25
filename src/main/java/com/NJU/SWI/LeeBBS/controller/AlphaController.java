package com.NJU.SWI.LeeBBS.controller;

import com.NJU.SWI.LeeBBS.dao.AlphaDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.PrintWriter;

@Controller
@RequestMapping("/alpha")
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
    @RequestMapping(path = "/student/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id") int id){
//        System.out.println("met get id");
        return id + "<h1>GET student</h1>";
    }
}
