package org.example.shopping.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.shopping.comment.dto.CommentDetail;
import org.example.shopping.comment.dto.CommentInsertReq;
import org.example.shopping.comment.dto.CommentUpdateReq;
import org.example.shopping.comment.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    // 댓글 등록.
    @PostMapping("/insert")
    public void commentInsert(@RequestBody @Valid CommentInsertReq commentInfo) {

        commentService.insertComment(commentInfo);
    }

    // 댓글 수정
    @PostMapping("/update")
    public void commentUpdate(@RequestBody @Valid CommentUpdateReq commentInfo) {

        commentService.updateComment(commentInfo);
    }

    // 게시판 번호를 기반으로 댓글 뭉치를 불러오는 컨트롤러메서드.
    // 여기서 호출할게 아니라 게시판 디테일 들어갔을때 그 컨트롤러에서 호출하여 사용해야함.
    // 여기는 일단 만들어두고 test 용으로 남김. 이후 test 완료 시 주석 처리 예정.
    @GetMapping("/boardNo/{boardSeqNo}")
    public List<CommentDetail> getCommentInfoByBoardSeqNo(@PathVariable int boardSeqNo) {

        return commentService.getCommentByBoardSeqNo(boardSeqNo);
    }


    // 사용자 아이디를 기반으로 여테 해당 아이디가 작성한 댓글, 답글을 모아 볼수 있음.
    // 타인이 보면 안되기 때문에 여기는 자바단에서 인가확인 절차가 있어야 함.
    // 접근 가능한 주체들은 사용자(본인만), 관리자(관리용)
    @GetMapping("/user/{userId}")
    public List<CommentDetail> getCommentInfoByUserId(@PathVariable String userId) {

        return commentService.getCommentByUserId(userId);
    }


    // 댓글의 status 값을 변경하는 메소드.
    // 숨김, 비밀, 삭제 등의 상태가 있음.
    // 이또한 본인이 아니거나 관리자가 아니라면 작동을 허용하지 않도록 검증 작업이 필요함.
    @GetMapping("/statChange/{commentSeqNo}/{status}")
    public void commentStatusUpdate(@PathVariable("commentSeqNo") int commentSeqNo, @PathVariable("status") int status) {

        commentService.statusUpdateComment(commentSeqNo, status);
    }
}
