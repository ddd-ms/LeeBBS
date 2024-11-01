package com.NJU.SWI.LeeBBS.controller;

import com.NJU.SWI.LeeBBS.annotation.LoginRequired;
import com.NJU.SWI.LeeBBS.entity.DiscussPost;
import com.NJU.SWI.LeeBBS.entity.User;
import com.NJU.SWI.LeeBBS.service.DiscussPostService;
import com.NJU.SWI.LeeBBS.service.UserService;
import com.NJU.SWI.LeeBBS.util.BBSUtil;
import com.NJU.SWI.LeeBBS.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping("/discuss")
public class DiscussPostController {
    @Autowired
    private DiscussPostService discussPostService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public String addDiscussPost(String title,String content)
    {
        if(hostHolder.getUser() == null){
            return BBSUtil.jsonDumps(403,"你还没有登录哦");
        }
        DiscussPost p = new DiscussPost(
                hostHolder.getUser().getId(),
                title,
                content,
                0,
                0,
                new Date(),
                0,
                0
        );
        discussPostService.insertDiscussPost(p);
        return BBSUtil.jsonDumps(200,"发布成功");
    }
    @RequestMapping(value = "/detail/{pid}",method = RequestMethod.GET)
    public String getDiscussPostById(@PathVariable("pid")int id, Model model)
    {
        DiscussPost p = discussPostService.getDiscussPostById(id);
        model.addAttribute("post",p);

        User u = userService.findUserById(p.getUserId());
        model.addAttribute("user",u);

        return "/site/discuss-detail";
    }
}
