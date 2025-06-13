package org.example.shopping.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentUpdateReq {

    // 댓글 번호
    private int cmtSeqNo;

    // 댓글 작성자.
    private String writer;

    // 댓글 작성자 ID.
    private String writerID;

    // 댓글 내용.
    private String content;

}
