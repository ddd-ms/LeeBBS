package com.NJU.SWI.LeeBBS.service;

import com.NJU.SWI.LeeBBS.dao.DiscussPostMapper;
import com.NJU.SWI.LeeBBS.entity.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class DiscussPostService {
    @Autowired
    private DiscussPostMapper discussPostMapper;
    public List<DiscussPost> selectDiscussPostList(int userId, int offset, int limit){
        return discussPostMapper.selectDiscussPostList(userId,offset,limit);
    }
    public int selectCount(int userId){
        return discussPostMapper.selectCount(userId);
    }
    public int insertDiscussPost(DiscussPost p){
        if(p==null){
            throw new IllegalArgumentException("参数不能为空");
        }
        p.setTitle(HtmlUtils.htmlEscape(p.getTitle()));
        p.setContent(HtmlUtils.htmlEscape(p.getContent()));
//      使用api过滤敏感词
        return discussPostMapper.insertDiscussPost(p);
    }
    public DiscussPost getDiscussPostById(int id){
        return discussPostMapper.getDiscussPostById(id);
    }
}
