package com.NJU.SWI.LeeBBS.controller;

import com.NJU.SWI.LeeBBS.annotation.LoginRequired;
import com.NJU.SWI.LeeBBS.entity.Comment;
import com.NJU.SWI.LeeBBS.entity.DiscussPost;
import com.NJU.SWI.LeeBBS.entity.Page;
import com.NJU.SWI.LeeBBS.entity.User;
import com.NJU.SWI.LeeBBS.service.CommentService;
import com.NJU.SWI.LeeBBS.service.DiscussPostService;
import com.NJU.SWI.LeeBBS.service.UserService;
import com.NJU.SWI.LeeBBS.util.BBSUtil;
import com.NJU.SWI.LeeBBS.util.HostHolder;
import com.NJU.SWI.LeeBBS.util.LeeBBSConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/discuss")
public class DiscussPostController {
    @Autowired
    private DiscussPostService discussPostService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;

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
    @Value("${LeeBBS.page.comment.size}")
    private int commentPageSize;
    @RequestMapping(value = "/detail/{pid}",method = RequestMethod.GET)
    public String getDiscussPostById(@PathVariable("pid")int pid, Model model, Page page)
    {
        DiscussPost p = discussPostService.getDiscussPostById(pid);
        model.addAttribute("post",p);

        User u = userService.findUserById(p.getUserId());
        model.addAttribute("user",u);

        page.setLimit(commentPageSize);
        page.setPath("/discuss/detail/"+pid);
        page.setRows(p.getCommentCount());

        List<Comment> comments = commentService.selectCommentByEntity(
                LeeBBSConstant.ENTITY_TYPE_POST, pid, page.getOffset(), page.getLimit());
        List<Map<String,Object>> commentWithUser = new ArrayList<>();
        for(Comment c : comments){
            Map<String,Object> map = Map.of("comment",c,"user",userService.findUserById(c.getUserId()));
            List<Comment> reply = commentService.selectCommentByEntity(
                    LeeBBSConstant.ENTITY_TYPE_COMMENT, c.getId(), 0, Integer.MAX_VALUE);
            List<Map<String,Object>> replyWithUser = new ArrayList<>();
            for(Comment r : reply){
                Map<String,Object> map2 = Map.of("reply",r,"user",userService.findUserById(r.getUserId()));
                replyWithUser.add(map2);
            }
            map.put("reply",replyWithUser);
            map.put("replyCount",commentService.selectCount(LeeBBSConstant.ENTITY_TYPE_COMMENT,c.getId()));
            commentWithUser.add(map);
        }
        model.addAttribute("comments",commentWithUser);
        return "/site/discuss-detail";
    }
}
