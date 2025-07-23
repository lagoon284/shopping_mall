package org.example.shopping.comment.service;

import lombok.RequiredArgsConstructor;
import org.example.shopping.comment.dto.CommentDetail;
import org.example.shopping.comment.dto.CommentInsertReq;
import org.example.shopping.comment.dto.CommentUpdateReq;
import org.example.shopping.comment.mapper.CommentMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;

    public void insertComment(CommentInsertReq commentInfo) {

        commentMapper.insertComment(commentInfo);
    }

    public List<CommentDetail> getCommentByBoardSeqNo(int boardSeqNo) {

        return commentMapper.getCommentByBoardSeqNo(boardSeqNo);
    }

    public List<CommentDetail> getCommentByUserId(String userId) {

        return commentMapper.getCommentByUserId(userId);
    }

    public void updateComment(CommentUpdateReq commentInfo) {

        commentMapper.updateComment(commentInfo);
    }

    public void statusUpdateComment(int commentSeqNo, int status) {

        commentMapper.statusUpdateComment(commentSeqNo, status);
    }
}
