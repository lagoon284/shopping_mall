package org.example.shopping.board.mapper;

import org.example.shopping.board.dto.BoardDetail;
import org.example.shopping.board.dto.BoardInsertReq;
import org.example.shopping.board.dto.BoardUpdateReq;

import java.util.List;

public interface BoardMapper {

    void insertBoard(BoardInsertReq boardInfo);

    void updateBoard(BoardUpdateReq boardInfo);

    List<BoardDetail> getBoardInfoForList();

    BoardDetail getBoardInfoBySeqNo(Long seqNo);

    List<BoardDetail> getBoardInfoListByKeyword(String keyword);

    void viewedPostPlus(int seqNo);
}
