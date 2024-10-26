package com.NJU.SWI.LeeBBS.service;

import com.NJU.SWI.LeeBBS.dao.DiscussPostMapper;
import com.NJU.SWI.LeeBBS.entity.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
