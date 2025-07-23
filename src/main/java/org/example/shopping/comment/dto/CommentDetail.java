package org.example.shopping.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.example.shopping.util.common.dto.RetAttributes;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDetail extends RetAttributes {

    // 댓글 번호
    private int cmtSeqNo;

    // 댓글 작성자.
    private String writer;

    // 댓글 작성자 ID.
    private String writerID;

    // 댓글 내용.
    private String content;

    // 게시글(부모) 번호
    private int boardSeqNo;

    // 댓글(부모) 번호 - 답글(대댓글) 구조
    private int parentSeqNo;

    // 댓글 상태 값 (삭제, 숨김 처리 등)
    private int status;
}
