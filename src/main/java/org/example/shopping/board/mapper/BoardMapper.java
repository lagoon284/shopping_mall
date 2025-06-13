package org.example.shopping.board.mapper;

import org.example.shopping.board.dto.BoardInfo;
import org.example.shopping.board.dto.BoardInsertReq;
import org.example.shopping.board.dto.BoardUpdateReq;

import java.util.List;

public interface BoardMapper {

    void insertBoard(BoardInsertReq boardInfo);

    void updateBoard(BoardUpdateReq boardInfo);

    BoardInfo getBoardInfoBySeqNo(Long seqNo);

    List<BoardInfo> getBoardInfoListByKeyword(String keyword);
}
