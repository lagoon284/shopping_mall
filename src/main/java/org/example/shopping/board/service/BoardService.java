package org.example.shopping.board.service;

import lombok.RequiredArgsConstructor;
import org.example.shopping.board.dto.BoardInfo;
import org.example.shopping.board.dto.BoardInsertReq;
import org.example.shopping.board.dto.BoardUpdateReq;
import org.example.shopping.board.mapper.BoardMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;

    public void insertBoard(BoardInsertReq boardInfo) {

        boardMapper.insertBoard(boardInfo);
    }

    public BoardInfo getBoardInfoBySeqNo(Long seqNo) {

        return boardMapper.getBoardInfoBySeqNo(seqNo);
    }

    public List<BoardInfo> getBoardInfoListByKeyword(String keyword) {

        return boardMapper.getBoardInfoListByKeyword(keyword);
    }

    public void updBoard(BoardUpdateReq boardInfo) {

        boardMapper.updateBoard(boardInfo);
    }
}
