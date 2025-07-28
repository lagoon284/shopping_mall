import React, {useEffect, useState} from "react";
import axios from "axios";
import {Link, useNavigate} from "react-router-dom";

import {CommonType} from "../../interfaces/CommonInterface";
import { BoardListType } from "../../interfaces/BoardInterface";

export default function BoardList() {
    const navigate = useNavigate();

    const [boards, setBoards] = useState<BoardListType[]>([]);

    // 로딩 상태 관리
    const [ commonState, setCommonStat ] = useState<CommonType>({
        loading: true,
        error: ""
    });

    const fetchBoardList = () => {
        axios.get('http://localhost:8080/api/board/getList')
        .then(res => {
            setBoards(res.data.data);
        })
        .catch(error => {
            console.log('Error fetching data:', error);

            setCommonStat(prev => ({
                ...prev,
                error: "조회 중 문제가 발생했습니다. : " + error
            }));
        })
        .finally(() => {
            setCommonStat(prev => ({
                ...prev,
                loading: false
            }));
        })
    }

    function fnTrClickEvent(seqNo: number) {
        navigate(`/board/${seqNo}`);
    }

    useEffect(() => {
        fetchBoardList();
    }, []);

    if (commonState.loading) {
        return (
            <div className={'Loading'}>
                <h1>로딩 중 입니다. 기다리세요. </h1>
            </div>
        )
    }

    if (commonState.error) {
        return (
            <div className={'Error'}>
                <h1>error message : {commonState.error}</h1>
            </div>
        )
    }

    return (
        <>
            <h2 className={"section-title"}>게시글 리스트</h2>
            <div className={"divider"}/>
            <p/>
            <button style={{marginLeft: "auto", display: "block"}}>
                <Link to={"/board/boardReg"}>게시글 등록</Link>
            </button>
            <ul>
                <table>
                    <thead>
                    <tr>
                        <th>글 번호</th>
                        <th>제목</th>
                        <th>작성자</th>
                        <th>조회수</th>
                        <th>좋아요</th>
                        <th>댓글</th>
                        <th>작성일자</th>
                    </tr>
                    </thead>
                    <tbody>
                    {boards.length === 0 ? (
                        <tr>
                            <td colSpan={5}>
                                게시글이 없습니다.
                            </td>
                        </tr>
                    ) : (
                        boards.map((board) => (
                            <tr className={"link-tr"}
                                onClick={() => fnTrClickEvent(board.seqNo)}
                                key={board.seqNo}>
                                <td>{board.seqNo}</td>
                                <td>{board.title}</td>
                                <td>{board.writer}</td>
                                <td>{board.viewCnt}</td>
                                <td>{board.likeCnt}</td>
                                <td>{board.commentCnt}</td>
                                <td>{board.updDate ? (<> {board.updDate} <small> (수정됨)</small> </>) : (board.regDate)}</td>
                            </tr>
                        ))
                    )}
                    </tbody>
                </table>
            </ul>
        </>
    );
}