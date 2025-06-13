package org.example.shopping.board.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.shopping.board.dto.BoardInfo;
import org.example.shopping.board.dto.BoardInsertReq;
import org.example.shopping.board.dto.BoardUpdateReq;
import org.example.shopping.board.service.BoardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;

    // 게시글 하나 등록
    @PostMapping("/insert")
    public void boardInsert(@RequestBody @Valid BoardInsertReq boardInfo) {

        boardService.insertBoard(boardInfo);
    }

    // 다중 등록 기능 없을 것.

    // 게시글 한 개 정보 가져오기 (게시글 seqNo 번호 기준)
    @GetMapping("/seq/{seqNo}")
    public BoardInfo getBoardInfoBySeqNo(@PathVariable Long seqNo) {

        return boardService.getBoardInfoBySeqNo(seqNo);
    }

    // 키워드 검색
    @GetMapping("/research/{keyword}")
    public List<BoardInfo> getBoardInfoListByKeyword(@PathVariable String keyword) {

        return boardService.getBoardInfoListByKeyword(keyword);
    }

    // 게시글 수정
    // 작성자 본인이나 관리자가 아니라면 작동을 허용하면 안됨.
    @PostMapping("/update")
    public void updBoard(@RequestBody @Valid BoardUpdateReq boardInfo) {

        boardService.updBoard(boardInfo);
    }

    // 삭제는 상태값 변경. 상태값 아직 미정.

}
