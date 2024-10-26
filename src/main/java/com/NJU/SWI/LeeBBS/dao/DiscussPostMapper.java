package com.NJU.SWI.LeeBBS.dao;

import com.NJU.SWI.LeeBBS.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {
    int selectCount(@Param("uid") int userId);
    List<DiscussPost> selectDiscussPostList(int userId,int offset, int limit);

}
