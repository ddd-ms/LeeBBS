package com.NJU.SWI.LeeBBS.dao;

import com.NJU.SWI.LeeBBS.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    List<Comment> selectCommentByEntity(int entityType, int entityId, int offset, int limit);
    int selectCount(int entityType, int entityId);
    int insertComment(Comment comment);
    int removeComment(int id);
}
