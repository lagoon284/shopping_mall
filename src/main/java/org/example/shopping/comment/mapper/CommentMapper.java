package org.example.shopping.comment.mapper;

import org.example.shopping.comment.dto.CommentDetail;
import org.example.shopping.comment.dto.CommentInsertReq;
import org.example.shopping.comment.dto.CommentUpdateReq;

import java.util.List;

public interface CommentMapper {

    void insertComment(CommentInsertReq commentInfo);

    void updateComment(CommentUpdateReq commentInfo);

    List<CommentDetail> getCommentByBoardSeqNo(int boardSeqNo);

    List<CommentDetail> getCommentByUserId(String userId);

    void statusUpdateComment(int commentSeqNo, int status);
}
