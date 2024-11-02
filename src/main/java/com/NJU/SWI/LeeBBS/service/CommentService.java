package com.NJU.SWI.LeeBBS.service;

import com.NJU.SWI.LeeBBS.dao.CommentMapper;
import com.NJU.SWI.LeeBBS.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    public int addComment(Comment comment){
        return commentMapper.insertComment(comment);
    }
    public int removeComment(int id){
        return commentMapper.removeComment(id);
    }
    public List<Comment> selectCommentByEntity(int entityType, int entityId, int offset, int limit){
        return commentMapper.selectCommentByEntity(entityType,entityId,offset,limit);
    }
    public int selectCount(int entityType, int entityId){
        return commentMapper.selectCount(entityType,entityId);
    }
}
