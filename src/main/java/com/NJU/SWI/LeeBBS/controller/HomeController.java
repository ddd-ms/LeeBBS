package com.NJU.SWI.LeeBBS.controller;

import com.NJU.SWI.LeeBBS.entity.DiscussPost;
import com.NJU.SWI.LeeBBS.entity.Page;
import com.NJU.SWI.LeeBBS.service.DiscussPostService;
import com.NJU.SWI.LeeBBS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    private DiscussPostService discussPostService;
    @Autowired
    private UserService userService;
    @RequestMapping(path ="/index",method = RequestMethod.GET)
    public String getIndex(Model model, Page page) {
        try{
            page.setRows(discussPostService.selectCount(0));
            page.setPath("/index");


            List<DiscussPost> posts = discussPostService.selectDiscussPostList(0, page.getOffset(), page.getLimit());
            List<Map<String,Object>> postWithUsr = new ArrayList<>();
            if(!posts.isEmpty()){
                for(DiscussPost p:posts){
                    Map<String,Object> map = Map.of(
                            "post",p,
                            "user",userService.findUserById(p.getUserId())
                    );
                    postWithUsr.add(map);
                }
            }
            model.addAttribute("posts",postWithUsr);
//          SpringMVC会自动实例化Model和page，并将page注入到Model，然后向模板文件传入，可用${page.xxx}访问数据
//          这里可以省略下方的addAttribute
            model.addAttribute("page",page);
//            System.out.println("ok");
//            Iterator<Map<String, Object>> it = postWithUsr.iterator();
//            while (it.hasNext()) {
//                Map<String, Object> map = it.next();
//                System.out.println(map.get("user")+" posts: "+map.get("post"));
//            }
            return "/index";
        }catch(Exception e){
            e.printStackTrace();
            return "/error/500";
        }
    }

}
